package com.example.projectmanager.view_models

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Utilites.SingleLiveEvent
import com.example.projectmanager.data.interfaces.IUserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class AuthViewModel (
    private val repository: IUserRepository
) : ViewModel() {

    var username: String? = null
    var email: String? = null
    var password: String? = null
    var repeatedPassword: String? = null

    var event = SingleLiveEvent<AuthEvent>()

    private val disposables = CompositeDisposable()

    fun login() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            event.value = AuthEvent(AuthStatus.Failure, "Invalid email or password")
            return
        }

        event.value = AuthEvent(AuthStatus.Started)

        val disposable = repository.login(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // PASS
                event.value = AuthEvent(AuthStatus.Success)
            }, {
                // FAIL
                event.value = AuthEvent(AuthStatus.Failure, it.message!!)
            })

        disposables.add(disposable)
    }

    fun register() {
        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            event.value = AuthEvent(AuthStatus.Failure, "Invalid email or password")
            return
        }

        event.value = AuthEvent(AuthStatus.Started)

        val disposable = repository.register(email!!, password!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                event.value = AuthEvent(AuthStatus.Success)
            }, {
                event.value = AuthEvent(AuthStatus.Failure, it.message!!)
            })

        disposables.add(disposable)
    }

    fun forwardToSignUp(view: View) {

    }

    fun forwardToLogin(view: View) {

    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class AuthEvent(var status: AuthStatus, var error: String? = null)
    enum class AuthStatus
    {
        Started,
        Success,
        Failure
    }
}