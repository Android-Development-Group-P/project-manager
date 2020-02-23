package com.example.projectmanager.ui.chat

import android.util.Log
import android.view.View
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

    var event = SingleLiveEvent<ChatEvent>()

    private val disposables = CompositeDisposable()


    fun onCreateMessage() {
        if (message.isNotEmpty()) {
            val chatMessage = ChatMessageEntity("Johan_Turntable", "6wX8bHgRFt2LFDphrhD3", null, message)

            val disposable = repository.create("johan", chatMessage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    event.value = ChatEvent(ChatStatus.Success, null)
                }, {
                    event.value = ChatEvent(ChatStatus.Failure, it.localizedMessage)
                })

            disposables.add(disposable)

        } else {
            event.value = ChatEvent(ChatStatus.Failure, "Message field can not be empty")
        }
    }

    data class ChatEvent(var status: ChatStatus, var error: String? = null)
    enum class ChatStatus {
        Success,
        Failure
    }

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    fun registerMessageListener(channelId: String) : ListenerRegistration {
        return repository.addMessageListener(channelId, this::onMessagesChange)
    }

    fun onMessagesChange(messages: List<ChatMessageEntity>) {

    }
}