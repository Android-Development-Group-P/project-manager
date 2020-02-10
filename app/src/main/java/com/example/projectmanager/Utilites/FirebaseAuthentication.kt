package com.example.projectmanager.Utilites

import com.example.projectmanager.Interfaces.AuthenticationProvider
import com.google.firebase.auth.FirebaseAuth

class FirebaseAuthentication : AuthenticationProvider {

    private var auth = FirebaseAuth.getInstance()

    override fun login(
        email: String,
        password: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, it.exception!!.message.toString())
            }
        }
    }

    override fun register(
        email: String,
        password: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, it.exception!!.message.toString())
            }
        }
    }

    override fun logout(
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        auth.signOut()
        if (auth.currentUser == null) {
            callback(true, null)
        } else {
            callback(false, "User was not logged out.")
        }
    }

    override fun resetPassword(
        email: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                callback(true, null)
            } else {
                callback(false, "The reset of the password went wrong")
            }
        }
    }

    override fun changePassword(
        newPassword: String,
        callback: (isSuccessful: Boolean, error: String?) -> Unit
    ) {
        // TODO
    }

}