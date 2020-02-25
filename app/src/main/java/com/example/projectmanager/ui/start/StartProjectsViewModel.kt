package com.example.projectmanager.ui.start

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.LiveDataResult
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class StartProjectsViewModel (
    private val session: SessionProvider,
    private val repository: IProjectRepository
) : ViewModel() {

    private val _projects = MutableLiveData<LiveDataResult<List<ProjectEntity>>>()

    private val disposables = CompositeDisposable()

    init {
        loadProjects()
        // https://github.com/android/architecture-components-samples/blob/master/GithubBrowserSample/app/src/main/java/com/android/example/github/vo/Resource.kt
    }

    fun getProjects(): LiveData<LiveDataResult<List<ProjectEntity>>> = _projects

    fun loadProjects() {
        val ids = session.user!!.projects ?: listOf()
        Log.d("SessionTag", ids.toString())
        disposables.add(repository.getSectionByIds(ids)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _projects.postValue(LiveDataResult.success(it))
            }, {
                _projects.postValue(LiveDataResult.error(it))
            }
        ))
    }

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