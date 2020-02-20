package com.example.projectmanager.view_models

import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserCreationViewModel (
    private val repository: IUserRepository,
    private val session: SessionProvider
): ViewModel() {

    var displayName: String? = null

    var event = SingleLiveEvent<UserCreationEvent>()

    var disposables = CompositeDisposable()

    fun save() {
        event?.value = UserCreationEvent(UserCreationStatus.Started)

        session.user?.let { user ->

            user.displayName = displayName!!

            disposables.add(repository.update(user)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe({
                    session.createSession(user)
                    event?.value = UserCreationEvent(UserCreationStatus.Success)
                }, {
                    event?.value = UserCreationEvent(UserCreationStatus.Failure, "Could not upload 'UserEntity'.")
                })
            )

        } ?: run {
            event?.value = UserCreationEvent(UserCreationStatus.Failure, "No session set.")
        }
    }

    fun skip() { event?.value = UserCreationEvent(UserCreationStatus.Skipped) }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class UserCreationEvent(var status: UserCreationStatus, var error: String? = null)
    enum class UserCreationStatus
    {
        Started,
        Success,
        Skipped,
        Failure
    }
}