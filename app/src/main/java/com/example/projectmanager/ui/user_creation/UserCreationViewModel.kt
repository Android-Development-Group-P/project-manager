package com.example.projectmanager.ui.user_creation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserCreationViewModel (
    private val session: SessionProvider,
    private val repository: IUserRepository
): ViewModel() {

    var displayName = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    /**
     * Save the created user
     */
    fun save() {
        _event.value =
            Event.Started()

        session.user?.let { user ->
            user.displayName = displayName.value

            disposables.add(repository.update(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    _event.value =
                        Event.Success()
                }, {
                    _event.value =
                        Event.Failure(
                            "Could not upload 'UserEntity'."
                        )
                })
            )

        } ?: run {
            _event.value =
                Event.Failure(
                    "No session set."
                )
        }
    }

    /**
     * Skip the user creation
     */
    fun skip() { _event.value =
        Event.Skipped()
    }

    /**
     * Validates the view models fields
     * @return The status of the validation
     */
    private fun isValidated(): Boolean {
        return !displayName.value.isNullOrEmpty()
    }

    private val _formValidation = MediatorLiveData<Boolean>().apply {
        addSource(displayName) { value = isValidated() }
    }
    fun getFormValidation(): LiveData<Boolean> = _formValidation


    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class Started: Event()
        class Success: Event()
        class Skipped: Event()
        class Failure(val error: String): Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}