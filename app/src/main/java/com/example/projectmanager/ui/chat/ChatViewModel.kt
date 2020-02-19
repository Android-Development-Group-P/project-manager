package com.example.projectmanager.ui.chat

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.Models.ChatMessage
import com.example.projectmanager.utilites.SingleLiveEvent

class ChatViewModel : ViewModel() {
    var message: String = ""

    var chatEvent = SingleLiveEvent<ChatEvent>()

    fun onCreateMessage() {
        Log.d("asd", "wasd")
        if (message.isNotEmpty()) {
            val chatMessage = ChatMessage("Johan_Turntable", "6wX8bHgRFt2LFDphrhD3" , null, message)
            DatabaseManager.db.createChatMessage(chatMessage) { isSuccessful, error ->
                when (isSuccessful) {
                    true -> chatEvent.value = ChatEvent(ChatStatus.Success)
                    false -> chatEvent.value = ChatEvent(ChatStatus.Failure)
                }
            }
        }
        else {
            chatEvent.value = ChatEvent(ChatStatus.Failure, "Message field can not be empty")
        }
    }

    data class ChatEvent(var status: ChatStatus, var error: String? = null)
    enum class ChatStatus {
        Success,
        Failure
    }
}