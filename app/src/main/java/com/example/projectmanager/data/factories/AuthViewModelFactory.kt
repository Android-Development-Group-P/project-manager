package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IAccountRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.view_models.AuthViewModel

class AuthViewModelFactory (
    private val repository: IAccountRepository,
    private val session: SessionProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AuthViewModel(repository, session) as T
    }
}