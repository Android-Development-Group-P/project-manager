package com.example.projectmanager.data.providers.firebase

import android.content.Context
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.data.interfaces.services.IUserService
import com.google.firebase.auth.FirebaseAuth

class FBSessionImpl (
    context: Context,
    userService: IUserService
) : SessionProvider(context, userService) {

    private val auth = FirebaseAuth.getInstance()

    init {
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                removeSession()
            }
        }
    }
}