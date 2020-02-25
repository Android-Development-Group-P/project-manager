package com.example.projectmanager.ui.project_new

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.threeten.bp.LocalDateTime

class CreateProjectViewModel (
    private val session: SessionProvider,
    private val projectRepository: IProjectRepository,
    private val userRepository: IUserRepository
) : ViewModel() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    private val disposables = CompositeDisposable()

    /**
     * Create the project
     */
    fun create() {
        _event.value = Event.Started()

        if (!isValidated()) {
            _event.value = Event.Failure("Validation failed.")
            return
        }

        var projectId: String? = null

        disposables.add(projectRepository.create(ProjectEntity(
            title = title.value,
            description = description.value,
            password = password.value,
            members = listOf(session.user!!.id!!))
        ).flatMap {
                projectId = it
                userRepository.getById(session.user!!.id!!) }
            .flatMapCompletable {
                val user = it
                val projects = it.projects?.toMutableList() ?: mutableListOf()
                projects.add(projectId!!)
                user.projects = projects
                userRepository.update(user)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Session", "Success")
                _event.value = Event.Success()
            }, {
                Log.d("Session", "Failure" + it.message)
                _event.value = Event.Failure(it.message!!)
            })
        )
    }

    /**
     * Validates the view models fields
     * @return The status of the validation
     */
    private fun isValidated(): Boolean {
        return !title.value.isNullOrEmpty() && !description.value.isNullOrEmpty()
    }

    private val _formValidation = MediatorLiveData<Boolean>().apply {
        addSource(title) { value = isValidated() }
        addSource(description) { value = isValidated() }
    }
    fun getFormValidation(): LiveData<Boolean> = _formValidation

    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class Started: Event()
        class Success: Event()
        class Failure(val error: String): Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}