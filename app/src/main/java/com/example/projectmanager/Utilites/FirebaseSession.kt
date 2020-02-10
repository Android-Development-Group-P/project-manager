package com.example.projectmanager.Utilites

import com.example.projectmanager.Interfaces.SessionProvider
import com.google.firebase.auth.FirebaseAuth

class FirebaseSession : SessionProvider {

    private var auth = FirebaseAuth.getInstance()

    override var isActive: Boolean = false
    override var currentUser: CurrentUser? = null

    override fun setLoggedIn() {
        currentUser = CurrentUser(
            uid = "",
            displayName = "",
            email = ""
        )
    }

    override fun isLoggedIn(): Boolean {
        return (auth.currentUser != null)
    }
}