package com.example.projectmanager.data.providers.firebase

import android.content.Context
import com.example.projectmanager.data.interfaces.SessionProvider
import com.google.firebase.auth.FirebaseAuth

class FBSession : SessionProvider {

    private val auth = FirebaseAuth.getInstance()

    constructor(context: Context) : super(context) {
        auth.addAuthStateListener {
            if (auth.currentUser == null) {
                removeSession()
            }
        }
    }
}