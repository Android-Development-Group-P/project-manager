package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IProjectRepository
import com.example.projectmanager.ui.project_new.CreateProjectViewModel

class CreateProjectViewModelFactory (
    private val repository: IProjectRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateProjectViewModel(repository) as T
    }
}