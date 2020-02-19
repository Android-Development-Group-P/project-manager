package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.ui.issue.CreateIssueViewModel

class CreateIssueViewModelFactory (
    private val repository: IIssueRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CreateIssueViewModel(repository) as T
    }
}