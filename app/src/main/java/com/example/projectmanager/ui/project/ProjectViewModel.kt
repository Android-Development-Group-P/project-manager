package com.example.projectmanager.ui.project

import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.InviteCodeEntity
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProjectViewModel (
    private val inviteService: IInviteCodeService
) : ViewModel() {

    private val disposables = CompositeDisposable()

    fun startInviteDialog(projectId: String) {
        disposables.add(inviteService.getByProjectId(projectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _event.value = Event.StartInviteDialog(it)
            }, {
                _event.value = Event.Failure(it.message!!)
            }))
    }

    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class Success: Event()
        class StartInviteDialog(val code: InviteCodeEntity): Event()
        class Failure(val error: String): Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}