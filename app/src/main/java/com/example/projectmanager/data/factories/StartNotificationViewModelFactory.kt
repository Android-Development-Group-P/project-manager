package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.data.interfaces.repositories.IIssueRepository
import com.example.projectmanager.ui.start.StartNotificationViewModel

class StartNotificationViewModelFactory (
    private val session: SessionProvider,
    private val repository: IIssueRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return StartNotificationViewModel(session, repository) as T
    }
}