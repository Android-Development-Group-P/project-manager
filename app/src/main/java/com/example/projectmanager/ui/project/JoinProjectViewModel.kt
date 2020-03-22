package com.example.projectmanager.ui.project

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.InviteCodeEntity
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.*
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import com.example.projectmanager.data.interfaces.services.IProjectService
import com.example.projectmanager.util.CLASS_TAG
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class JoinProjectViewModel (
    private val session: SessionProvider,
    private val projectService: IProjectService,
    private val inviteService: IInviteCodeService
) : ViewModel() {

    var code = MutableLiveData<String>()
    var project = MutableLiveData<ProjectEntity>()

    private val disposables = CompositeDisposable()

    fun scan() { _event.value = Event.StartScanner() }

    fun search() {
        if (code.value.isNullOrEmpty())
            return

        disposables.add(inviteService.getById(code.value!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                code.value = it.id
                _event.value = Event.InviteCodeFound()
                loadProject(it)
            }, {
                Log.d(CLASS_TAG, it.message!!)
                _event.value = Event.Failure(it.message!!)
            })
        )
    }

    fun joinProject() {
        disposables.add(projectService.join(session.user!!.id!!, project.value!!.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _event.value = Event.Success()
            }, {
                Log.d(CLASS_TAG, it.message!!)
                _event.value = Event.Failure(it.message!!)
            })
        )
    }

    private fun loadProject(code: InviteCodeEntity) {
        disposables.add(projectService.getById(code.projectId!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                project.value = it
                _event.value = Event.ProjectLoaded()
            }, {
                Log.d(CLASS_TAG, it.message!!)
                _event.value = Event.Failure(it.message!!)
            })
        )
    }

    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class StartScanner: Event()
        class InviteCodeFound: Event()
        class ProjectLoaded(): Event()
        class Success: Event()
        class Failure(val error: String): Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}