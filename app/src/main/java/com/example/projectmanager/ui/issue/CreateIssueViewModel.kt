package com.example.projectmanager.ui.issue

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.SimpleDateFormat
import java.util.*

class CreateIssueViewModel (
    private val repository: IIssueRepository
) : ViewModel() {

    var title: String = ""
    var description: String = ""

    var event = SingleLiveEvent<IssueEvent>()

    private val disposables = CompositeDisposable()

    fun onCreateIssue(view: View) {
        if (title.isNotEmpty() && description.isNotEmpty()) {

            val created = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date());

            val issue = IssueEntity("test_user", created, title, description, "High",
                null, "red", null, "started", "test_project")

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
        Success,
        Failure
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}
