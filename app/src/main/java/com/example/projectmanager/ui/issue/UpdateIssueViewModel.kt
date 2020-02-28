package com.example.projectmanager.ui.issue

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UpdateIssueViewModel (
    private val repository: IIssueRepository
) : ViewModel() {

    lateinit var title: String
    lateinit var description: String
    lateinit var status: String
    lateinit var issueEntity: IssueEntity
    lateinit var priority: String
    lateinit var label: String
    lateinit var assignedUser: String
    lateinit var department: String

    var event = SingleLiveEvent<UpdateIssueEvent>()

    private val disposables = CompositeDisposable()

    fun setText(issue: IssueEntity) {
        issueEntity = issue
        title = issue.title!!
        description = issue.description!!
        status = issue.status!!
        priority = issue.priority!!
        label = issue.label!!
        assignedUser = issue.assigned_user!!
        department = issue.area!!
    }

    fun onUpdateIssue() {
        if (title.isNotEmpty() && description.isNotEmpty()) {

            val issue = IssueEntity(null, issueEntity.creator, issueEntity.created, title, description, priority,
                assignedUser, label, department, status, issueEntity.project)

            val disposable = repository.updateIssue(issue, issueEntity.id!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // PASS
                    event.value = UpdateIssueEvent(UpdateIssueStatus.Success, null)
                }, {
                    // FAIL
                    event.value = UpdateIssueEvent(UpdateIssueStatus.Failure, it.localizedMessage)
                })

            disposables.add(disposable)
        } else {
            event.value = UpdateIssueEvent(UpdateIssueStatus.Failure, "You must write something in both fields")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }


    data class UpdateIssueEvent(var status: UpdateIssueStatus, var error: String? = null)
    enum class UpdateIssueStatus
    {
        Started,
        Success,
        Failure,
    }
}