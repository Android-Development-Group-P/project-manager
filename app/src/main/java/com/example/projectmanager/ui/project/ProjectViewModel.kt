package com.example.projectmanager.ui.project

import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ProjectViewModel(
    private val repository: IProjectRepository
) : ViewModel() {

    private val disposables = CompositeDisposable()

    var event = SingleLiveEvent<ProjectEvent>()

    fun loadAllProjects(ids: List<String>) {
        event.value = ProjectEvent(ProjectStatus.started)
        val disposable = repository.getSectionByIds(ids)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({projects ->
                // PASS
                event.value = ProjectEvent(ProjectStatus.Success, null, projects)
            }, {
                // FAIL
                event.value = ProjectEvent(ProjectStatus.Failure, it.localizedMessage)
            })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class ProjectEvent(var status: ProjectStatus, var error: String? = null, val projects: List<ProjectEntity>? = null)
    enum class ProjectStatus
    {
        started,
        Success,
        Failure
    }

}
