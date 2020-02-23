package com.example.projectmanager.data.interfaces.listeners

interface AuthListener {
    fun onStarted()
    fun onSuccess()
    fun onFailure(error: String)
}