package com.example.projectmanager.ui.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.interfaces.IAccountRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import com.example.projectmanager.util.Validation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginViewModel (
    private val session: SessionProvider,
    private val accountRepository: IAccountRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    fun login() {
        _event.value = Event.Started()

        if (!isValidated()) {
            _event.value = Event.Failure("Invalid email or password")
            return
        }

        disposables.add(accountRepository.login(email.value!!, password.value!!)
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
        return Validation.isEmail(email.value!!) && Validation.isPassword(password.value)
    }

    private val _formValidation = MediatorLiveData<Boolean>().apply {
        addSource(email) { value = isValidated() }
        addSource(password) { value = isValidated() }
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