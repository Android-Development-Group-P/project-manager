package com.example.projectmanager.Interfaces

import com.example.projectmanager.Utilites.CurrentUser

interface SessionProvider {

    var isActive: Boolean
    var currentUser: CurrentUser?

    fun setLoggedIn()
    fun isLoggedIn(): Boolean
}