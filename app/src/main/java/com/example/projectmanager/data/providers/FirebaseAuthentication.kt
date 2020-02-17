package com.example.projectmanager.data.providers

import android.util.Log
import com.example.projectmanager.data.entities.User
import com.example.projectmanager.data.interfaces.AuthenticationProvider
import com.example.projectmanager.data.managers.SessionManager
import com.example.projectmanager.data.network.AuthResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest


class FirebaseAuthentication : AuthenticationProvider {

    private var auth = FirebaseAuth.getInstance()

    override fun login(
        email: String,
        password: String,
        callback: (result: AuthResponse) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val user = createUser()
                SessionManager.createSession(user)

                callback(AuthResponse(true, null))
            } else {
                callback(AuthResponse(false, it.exception?.message.toString()))
            }
        }
    }

    override fun register(
        username: String,
        email: String,
        password: String,
        callback: (result: AuthResponse) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                val updates = UserProfileChangeRequest.Builder()
                    .setDisplayName(username).build()

                auth.currentUser!!.updateProfile(updates).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val user = createUser()
                        SessionManager.createSession(user)

                        callback(AuthResponse(true, null))
                    } else {
                        callback(AuthResponse(false, it.exception?.message.toString()))
                    }
                }
            } else {
                callback(AuthResponse(false, it.exception?.message.toString()))
            }
        }
    }

    override fun logout(
        callback: (result: AuthResponse) -> Unit
    ) {
        auth.signOut()
        if (auth.currentUser == null) {
            SessionManager.removeSession()

            callback(AuthResponse(true, null))
        } else {
            callback(AuthResponse(false, "User was not logged out."))
        }
    }

    override fun resetPassword(
        email: String,
        callback: (result: AuthResponse) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(AuthResponse(true, null))
            } else {
                callback(AuthResponse(false, "The reset of the password went wrong"))
            }
        }
    }

    override fun changePassword(
        newPassword: String,
        callback: (result: AuthResponse) -> Unit
    ) {
        // TODO
    }

    private fun createUser() = User(
        auth.currentUser!!.uid,
        auth.currentUser!!.displayName,
        auth.currentUser!!.email)
}