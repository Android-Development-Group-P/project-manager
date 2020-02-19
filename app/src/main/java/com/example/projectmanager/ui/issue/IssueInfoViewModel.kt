package com.example.projectmanager.ui.issue

import android.icu.text.CaseMap
import android.util.Log
import androidx.lifecycle.ViewModel
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

    var lastId: String = ""

    var event = SingleLiveEvent<IssueEvent>()

    private val disposables = CompositeDisposable()

    fun onGetProject(issueId: String) {
        if (issueId != lastId) {

            val disposable = repository.getIssue(issueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({issue ->
                    // PASS
                    Log.d("test", "${issue}")
                    title = "Title: ${issue.title}"
                    description = "Description: ${issue.description}"
                    status = "Status: ${issue.status}"

                    event.value = IssueEvent(IssueStatus.Success, null, title, description, status)
                }, {
                    // FAIL
                    event.value = IssueEvent(IssueStatus.Failure, it.localizedMessage)
                })

            disposables.add(disposable)

            lastId = issueId

        } else {
            Log.d("test", "same id")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }


    data class IssueEvent(var status: IssueStatus, var error: String? = null, var title: String? = null, var description: String? = null, var issueStatus: String? = null)
    enum class IssueStatus
    {
        Success,
        Failure
    }
}