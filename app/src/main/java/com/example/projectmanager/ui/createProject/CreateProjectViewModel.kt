package com.example.projectmanager.ui.createProject

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.Models.Project
import com.example.projectmanager.utilites.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class CreateProjectViewModel : ViewModel() {
    var title: String = ""
    var description: String = ""
    var checked: Boolean = true

    var createProjectEvent = SingleLiveEvent<ProjectEvent>()

    fun onCreateProject(view: View) {
        if (title.isNotEmpty() && description.isNotEmpty()) {

            // TODO: get uid from logged in user
            val uid = "test123"

            var project = Project()
            if (checked) {
                var code = createCode()
                while (code == "") {
                    code = createCode()
                }
                project = Project(title, description, listOf(uid), false, null, code)
            } else {
                project = Project(title, description, listOf(uid), false, null, null)
            }

            DatabaseManager.db.createNewProject(project) { status, projectId, error ->
                if (status)  {
                    // TODO: Add user to project
                    createProjectEvent.value = ProjectEvent(ProjectStatus.Success)
                } else {
                    createProjectEvent.value = ProjectEvent(ProjectStatus.Failure, error)
                }
            }

        } else {
            createProjectEvent.value = ProjectEvent(ProjectStatus.Failure, "You must write something in both fields")
        }
    }

    private fun checkCode(code: String, callback: (status: Boolean) -> Unit) {
        GlobalScope.launch {
            DatabaseManager.db.checkIfCodeExists(code) { status, error ->
                if (status) {
                    callback(false)
                } else {
                    callback(true)
                }
            }
        }
    }

    private fun createCode(): String {
        val STRING_CHARACTERS = ('0'..'z').toList().toTypedArray()
        var code = (1..8).map { STRING_CHARACTERS.random() }.joinToString("")

        runBlocking { checkCode(code) {status ->
            if (!status) {
                code = ""
            }
        } }

        return code
    }

    data class ProjectEvent(var status: ProjectStatus, var error: String? = null)
    enum class ProjectStatus
    {
        Success,
        Failure
    }
}
