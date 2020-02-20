package com.example.projectmanager.view_models

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.util.SingleLiveEvent
import com.example.projectmanager.data.interfaces.IAccountRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class AuthViewModel (
    private val accountRepository: IAccountRepository,
    private val userRepository: IUserRepository,
    private val session: SessionProvider
) : ViewModel() {

    var email: String? = null
    var password: String? = null
    var repeatedPassword: String? = null

    var event = SingleLiveEvent<AuthEvent>()

    private val disposables = CompositeDisposable()

    fun login() {

        event.value = AuthEvent(AuthStatus.Started)

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            event.value = AuthEvent(AuthStatus.Failure, "Invalid email or password")
            return
        }

        disposables.add(accountRepository.login(email!!, password!!)
            .flatMap { id -> userRepository.getById(id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ user ->
                session.createSession(user)
                event.value = AuthEvent(AuthStatus.Success)
            }, {
                event.value = AuthEvent(AuthStatus.Failure, it.message!!)
            })
        )
    }

    fun register() {

        event.value = AuthEvent(AuthStatus.Started)

        if (email.isNullOrEmpty() || password.isNullOrEmpty()) {
            event.value = AuthEvent(AuthStatus.Failure, "Invalid email or password")
            return
        }

        disposables.add(accountRepository.register(email!!, password!!)
            .flatMap { id -> userRepository.create(UserEntity(id = id, email = email)) }
            .flatMap { id -> userRepository.getById(id) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe({ user ->
                session.createSession(user)
                event.value = AuthEvent(AuthStatus.Success)
            }, {
                event.value = AuthEvent(AuthStatus.Failure, it.message!!)
            })
        )
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