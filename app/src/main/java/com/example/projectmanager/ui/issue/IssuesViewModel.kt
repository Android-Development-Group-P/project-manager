package com.example.projectmanager.ui.issue

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.util.LiveDataResult
import com.example.projectmanager.util.SingleLiveEvent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class IssuesViewModel (
    private val repository: IIssueRepository
) : ViewModel() {

    lateinit var projectId: String
    lateinit var status: String

    private val _issues = MutableLiveData<LiveDataResult<List<IssueEntity>>>()

    private val disposables = CompositeDisposable()

    fun initFun() {
        loadIssues()
    }

    fun getIssues(): LiveData<LiveDataResult<List<IssueEntity>>> = _issues

    fun loadIssues() {
        disposables.add(repository.getAllIssuesForProjectByStatus(projectId, status)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _issues.postValue(LiveDataResult.success(it))
            }, {
                _issues.postValue(LiveDataResult.error(it))
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