package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.*
import com.example.projectmanager.data.interfaces.services.IInviteCodeService
import com.example.projectmanager.data.interfaces.services.IProjectService
import com.example.projectmanager.ui.project.JoinProjectViewModel

class JoinProjectViewModelFactory (
    private val session: SessionProvider,
    private val projectService: IProjectService,
    private val inviteService: IInviteCodeService
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return JoinProjectViewModel(session, projectService, inviteService) as T
    }
}