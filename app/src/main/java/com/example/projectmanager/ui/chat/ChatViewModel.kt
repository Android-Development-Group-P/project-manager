package com.example.projectmanager.ui.chat

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.interfaces.IChatRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.util.LiveDataResult
import com.example.projectmanager.util.SingleLiveEvent
import com.example.projectmanager.util.default
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ChatViewModel (
    private val session: SessionProvider,
    private val repository: IChatRepository
) : ViewModel() {

    lateinit var projectId: String
    var message = MutableLiveData<String>().default("")

    private val _messages by lazy {
        val liveData = MutableLiveData<LiveDataResult<List<ChatMessageEntity>>>()
        loadMessages()
        return@lazy liveData
    }
    private val disposables = CompositeDisposable()

    fun loadLatestMessage() {
        disposables.add(repository.listener.getMessageById(projectId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ message ->
               Log.d("123", message.message)
            }, {error ->
                _messages.postValue(LiveDataResult.error(error))
            })
        )
    }

    fun loadMessages() {
        disposables.add(repository.getAllBySection(projectId,20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ messages ->
                Log.d("123", messages.toString())
                _messages.postValue(LiveDataResult.success(messages))
            }, {error ->
                _messages.postValue(LiveDataResult.error(error))
            })
        )
    }

    fun getMessages() : LiveData<LiveDataResult<List<ChatMessageEntity>>> = _messages

    fun onCreateMessage(view: View) {

        if (message.value!!.isNotEmpty()) {
            val chatMessage =
                ChatMessageEntity(session.user!!.id!!, projectId, null, message.value!!)

            val disposable = repository.create(projectId, chatMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _event.value = Event.Success()
                }, {
                    _event.value = Event.Failure(it.localizedMessage)
                })

            disposables.add(disposable)

        } else {
            _event.value = Event.Failure("Message field can not be empty")
        }
    }


    private val _event = SingleLiveEvent<Event>()
    fun getEvent(): SingleLiveEvent<Event> = _event

    sealed class Event {
        class Started : Event()
        class Success : Event()
        class Failure(val error: String) : Event()
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}