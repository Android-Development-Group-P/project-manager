package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.interfaces.IIssueRepository
import com.example.projectmanager.ui.issue.IssueInfoViewModel

class IssueInfoViewModelFactory (
    private val repository: IIssueRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return IssueInfoViewModel(repository) as T
    }
}