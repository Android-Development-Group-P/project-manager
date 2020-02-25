package com.example.projectmanager.ui.chat

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.interfaces.IChatRepository
import com.example.projectmanager.util.SingleLiveEvent
import com.google.firebase.firestore.ListenerRegistration
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class ChatViewModel (
    private val repository: IChatRepository
) : ViewModel() {

    var message: String = ""
    var messageList: List<ChatMessageEntity> = ArrayList()

    private val _messages = MutableLiveData<ChatMessageEntity>()
    private val disposables = CompositeDisposable()

    fun loadMessages() {
        disposables.add(repository.getAllBySection("6wX8bHgRFt2LFDphrhD3",20)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({

            }, {

            })
        )
    }

    fun getMessages() : LiveData<ChatMessageEntity> = _messages

    fun onCreateMessage() {
        if (message.isNotEmpty()) {
            val chatMessage =
                ChatMessageEntity("Johan_Turntable", "6wX8bHgRFt2LFDphrhD3", null, message)

            val disposable = repository.create("johan", chatMessage)
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