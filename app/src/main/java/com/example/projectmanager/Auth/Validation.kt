package com.example.projectmanager.Auth

import android.util.Patterns

object Validation {

    fun isEmail(email: CharSequence) : Boolean {
        return email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPassword(password: CharSequence, repeatedPassword: CharSequence) : Boolean {
        return password.isEmpty() || password.length < 6 || password != repeatedPassword
    }
}