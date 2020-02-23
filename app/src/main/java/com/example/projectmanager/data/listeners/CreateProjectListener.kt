package com.example.projectmanager.data.listeners

interface CreateProjectListener {
    fun onValidated()
    fun onStarted()
    fun onSuccess()
    fun onFailure(error: String)
}