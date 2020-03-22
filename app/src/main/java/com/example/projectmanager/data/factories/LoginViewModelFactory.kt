package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.repositories.IAccountRepository
import com.example.projectmanager.data.interfaces.repositories.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.auth.LoginViewModel

class LoginViewModelFactory (
    private val session: SessionProvider,
    private val accountRepository: IAccountRepository,
    private val userRepository: IUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LoginViewModel(session, accountRepository, userRepository) as T
    }
}