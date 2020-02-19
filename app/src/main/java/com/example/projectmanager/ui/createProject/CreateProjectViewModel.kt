package com.example.projectmanager.ui.createProject

import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class CreateProjectViewModel (
    private val repository: IProjectRepository
) : ViewModel() {

    var title: String = ""
    var description: String = ""
    var checked: Boolean = true

    var event = SingleLiveEvent<ProjectEvent>()

    private val disposables = CompositeDisposable()

    fun onCreateProject(view: View) {
        if (title.isNotEmpty() && description.isNotEmpty()) {

            // TODO: get uid from logged in user
            val uid = "test123"

            var project = ProjectEntity()
            val created = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date());
            if (checked) {
                var code = createCode()
                while (code == "") {
                    code = createCode()
                }
                project = ProjectEntity(title, description, listOf(uid), false, null, code, created)
            } else {
                project = ProjectEntity(title, description, listOf(uid), false, null, null, created)
            }

            val disposable = repository.create(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    // PASS
                    event.value = ProjectEvent(ProjectStatus.Success)
                }, {
                    // FAIL
                    event.value = ProjectEvent(ProjectStatus.Failure, it.localizedMessage)
                })

            disposables.add(disposable)

        } else {
            event.value = ProjectEvent(ProjectStatus.Failure, "You must write something in both fields")
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    private fun createCode(): String {
        val STRING_CHARACTERS = ('0'..'z').toList().toTypedArray()
        var code = (1..8).map { STRING_CHARACTERS.random() }.joinToString("")

        runBlocking {
            GlobalScope.launch {
                val disposable = repository.checkIfCodeExists(code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({status ->
                        // PASS
                        if (!status) {
                            code = ""
                        }
                    }, {
                        // FAIL
                        code = ""
                    })

                disposables.add(disposable)
            }
        }

        return code
    }

    data class ProjectEvent(var status: ProjectStatus, var error: String? = null)
    enum class ProjectStatus
    {
        Success,
        Failure
    }
}
