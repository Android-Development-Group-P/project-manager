package com.example.projectmanager.data.providers.firebase

import android.content.Context
import com.example.projectmanager.data.interfaces.IUserRepository
import com.example.projectmanager.data.interfaces.SessionProvider
import com.google.firebase.auth.FirebaseAuth

class FBSessionImpl : SessionProvider {

    private val auth = FirebaseAuth.getInstance()

    constructor(context: Context, repository: IUserRepository) : super(context, repository) {
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                removeSession()
            }
        }
    }
}