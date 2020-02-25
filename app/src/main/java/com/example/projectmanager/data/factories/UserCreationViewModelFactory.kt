package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.user_creation.UserCreationViewModel

class UserCreationViewModelFactory (
    private val session: SessionProvider,
    private val repository: IUserRepository
): ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserCreationViewModel(
            session,
            repository
        ) as T
    }
}