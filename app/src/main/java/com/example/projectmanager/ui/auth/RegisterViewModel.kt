package com.example.projectmanager.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.repositories.IAccountRepository
import com.example.projectmanager.data.interfaces.repositories.IProjectRefRepository
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import com.example.projectmanager.util.Validation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class RegisterViewModel (
    private val session: SessionProvider,
    private val accountRepository: IAccountRepository,
    private val userRepository: IUserRepository,
    private val projectRefRepository: IProjectRefRepository
) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var repeatedPassword = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    fun register() {
        _event.value = Event.Started()

        if (!isValidated()) {
            _event.value = Event.Failure("All fields not set.")
            return
        }

        disposables.add(accountRepository.register(email.value!!, password.value!!)
            .flatMap { id -> userRepository.create(
                UserEntity(
                    id = id,
                    email = email.value)
            ) }
            .flatMap { id -> projectRefRepository.create(id) }
            .flatMap { id -> userRepository.getById(id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ user ->
                session.createSession(user)
                _event.value = Event.Success()
            }, {
                _event.value = Event.Failure(it.message!!)
            })
        )
    }


    /**
     * Validates the view models fields
     * @return The status of the validation
     */
    private fun isValidated(): Boolean {
        return Validation.isEmail(email.value) && Validation.isPassword(password.value, repeatedPassword.value)
    }

    private val _formValidation = MediatorLiveData<Boolean>().apply {
        addSource(email) { value = isValidated() }
        addSource(password) { value = isValidated() }
        addSource(repeatedPassword) { value = isValidated() }
        postValue(false)
    }
    fun getFormValidation(): LiveData<Boolean> = _formValidation


    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class Started: Event()
        class Success: Event()
        class Failure(val error: String): Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}