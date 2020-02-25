package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.start.StartProjectsViewModel

class StartProjectsViewModelFactory (
    private val session: SessionProvider,
    private val repository: IProjectRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartProjectsViewModel(session, repository) as T
    }
}