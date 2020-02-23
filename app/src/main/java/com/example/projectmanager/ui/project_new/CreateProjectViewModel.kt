package com.example.projectmanager.ui.project_new

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CreateProjectViewModel (
    private val repository: IProjectRepository
) : ViewModel() {

    var title = MutableLiveData<String>()
    var description = MutableLiveData<String>()

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

        disposables.add(repository.create(
            ProjectEntity("", "", "", listOf(""), false, "", "", ""))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _event.value = Event.Success()
            }, {
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