package com.example.projectmanager.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class HomeViewModel (
    private val repository: IIssueRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()

    var event = SingleLiveEvent<HomeEvent>()

    fun loadAllIssues(projectId: String) {
        event.value = HomeEvent(HomeStatus.started)
        val disposable = repository.getAllIssuesForProject(projectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({issues ->
                // PASS
                event.value = HomeEvent(HomeStatus.Success, null, issues)
            }, {
                // FAIL
                event.value = HomeEvent(HomeStatus.Failure, it.localizedMessage)
            })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    data class HomeEvent(var status: HomeStatus, var error: String? = null, val issues: List<IssueEntity>? = null)
    enum class HomeStatus
    {
        started,
        Success,
        Failure
    }
}