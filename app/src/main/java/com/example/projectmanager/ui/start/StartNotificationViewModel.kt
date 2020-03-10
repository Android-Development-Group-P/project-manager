package com.example.projectmanager.ui.start

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.util.LiveDataResult
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StartNotificationViewModel (
    private val session: SessionProvider,
    private val repository: IIssueRepository
) : ViewModel() {

    private val _notifications = MutableLiveData<LiveDataResult<List<IssueEntity>>>()

    private val disposables = CompositeDisposable()

    fun getNotifications() : LiveData<LiveDataResult<List<IssueEntity>>> = _notifications

    fun loadNotifications() {
        disposables.add(repository.getAllIssuesByAssignedUser(session.user!!.email!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _notifications.postValue(LiveDataResult.success(it))
            }, {
              _notifications.postValue(LiveDataResult.error(it))
            }
            ))
    }

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

