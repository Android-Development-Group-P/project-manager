package com.example.projectmanager.ui.issue

import android.icu.text.CaseMap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class IssueInfoViewModel (
    private val repository: IIssueRepository
) : ViewModel() {

    var title: String = ""
    var description: String = ""
    var status: String = ""

    var event = SingleLiveEvent<IssueEvent>()

    private val disposables = CompositeDisposable()

    fun getIssue(issueId: String) {
        event.value = IssueEvent(IssueStatus.Started)
        val disposable = repository.getIssue(issueId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({issue ->
                // PASS
                title = "Title: ${issue.title}"
                description = "Description: ${issue.description}"
                status = "Status: ${issue.status}"

                event.value = IssueEvent(IssueStatus.Success, null, issue)
            }, {
                // FAIL
                event.value = IssueEvent(IssueStatus.Failure, it.localizedMessage)
            })

        disposables.add(disposable)
    }

    fun deleteIssue(issueId: String) {
        val disposable = repository.deleteIssue(issueId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                // PASS
                event.value = IssueEvent(IssueStatus.DeleteSuccess)
            }, {
                // FAIL
                event.value = IssueEvent(IssueStatus.Failure, it.localizedMessage)
            })

        disposables.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }


    data class IssueEvent(var status: IssueStatus, var error: String? = null, var issue: IssueEntity? = null)
    enum class IssueStatus
    {
        Started,
        Success,
        Failure,
        DeleteSuccess
    }
}