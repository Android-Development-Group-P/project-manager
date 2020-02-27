package com.example.projectmanager.data.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.MainViewModel
import com.example.projectmanager.data.interfaces.SessionProvider

class MainViewModelFactory (
    val session: SessionProvider
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MainViewModel(session) as T
    }
}