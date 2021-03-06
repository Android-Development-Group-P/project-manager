package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.repositories.IChatRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.chat.ChatViewModel

class ChatViewModelFactory (
    private val session: SessionProvider,
    private val repository: IChatRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ChatViewModel(session, repository) as T
    }
}

