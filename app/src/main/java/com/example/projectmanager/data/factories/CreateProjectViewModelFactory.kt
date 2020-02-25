package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.project_new.CreateProjectViewModel

class CreateProjectViewModelFactory (
    private val sessionProvider: SessionProvider,
    private val projectRepository: IProjectRepository,
    private val userRepository: IUserRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateProjectViewModel(sessionProvider, projectRepository, userRepository) as T
    }
}