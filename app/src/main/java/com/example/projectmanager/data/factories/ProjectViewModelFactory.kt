package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import com.example.projectmanager.ui.project.ProjectViewModel

class ProjectViewModelFactory (
    private val service: IInviteCodeService
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProjectViewModel(service) as T
    }
}