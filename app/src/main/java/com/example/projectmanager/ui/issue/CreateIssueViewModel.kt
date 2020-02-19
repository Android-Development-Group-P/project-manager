package com.example.projectmanager.ui.issue

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.Models.Issue
import com.example.projectmanager.util.SingleLiveEvent
import java.text.SimpleDateFormat
import java.util.*

class CreateIssueViewModel : ViewModel() {
    var title: String = ""
    var description: String = ""

    var createIssueEvent = SingleLiveEvent<IssueEvent>()

    fun onCreateIssue(view: View) {
        if (title.isNotEmpty() && description.isNotEmpty()) {

            val created = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date());

            var issue = Issue("test_user", created, title, description, "High",
                null, "red", null, "started", "test_project")

            DatabaseManager.db.createIssue(issue) { status, error ->
                if (status) {
                    createIssueEvent.value = IssueEvent(IssueStatus.Success, null)
                } else {
                    createIssueEvent.value = IssueEvent(IssueStatus.Failure, error)
                }
            }

        } else {
            createIssueEvent.value = IssueEvent(IssueStatus.Failure, "You must write something in both fields")
        }
    }

    data class IssueEvent(var status: IssueStatus, var error: String? = null)
    enum class IssueStatus
    {
        Success,
        Failure
    }
}
