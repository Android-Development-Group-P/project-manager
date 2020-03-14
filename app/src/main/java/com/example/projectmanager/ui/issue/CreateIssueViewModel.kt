package com.example.projectmanager.ui.issue

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class CreateIssueViewModel (
    private val session: SessionProvider,
    private val repository: IIssueRepository
) : ViewModel() {

    var project: String = ""
    var title: String = ""
    var description: String = ""
    var priority: String = ""
    var label: String = ""
    var assignedUser: String = ""
    var department: String = ""

    var event = SingleLiveEvent<IssueEvent>()

    private val disposables = CompositeDisposable()

    fun setProjectId(projectId: String) {
        project = projectId
    }

    fun onCreateIssue(view: View) {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            event.value = IssueEvent(IssueStatus.Started)
            val created = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date());

            val issue = IssueEntity(null, session.user!!.id, created, title, description, priority,
                assignedUser, label, department, "Created", project)

            val disposable = repository.create(issue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // PASS
                    event.value = IssueEvent(IssueStatus.Success, null)
                }, {
                    // FAIL
                    event.value = IssueEvent(IssueStatus.Failure, it.localizedMessage)
                })

            disposables.add(disposable)

        } else {
            event.value = IssueEvent(IssueStatus.Failure, "You must write something in both fields")
        }
    }

    data class IssueEvent(var status: IssueStatus, var error: String? = null)
    enum class IssueStatus
    {
        Started,
        Success,
        Failure
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
