package com.example.projectmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.interfaces.SessionProvider

class MainViewModel (
    val session: SessionProvider
) : ViewModel() {

    private val _isLoggedIn = MutableLiveData<Boolean>()

    fun isLoggedIn() : LiveData<Boolean> = _isLoggedIn

    init {
        if (session.isLoggedIn()) {
            session.startListener()
            _isLoggedIn.postValue(true)
        } else {
            _isLoggedIn.postValue(false)
        }
    }
}