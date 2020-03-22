package com.example.projectmanager.util

import android.util.Patterns

object Validation {

    fun isEmail(email: String?) : Boolean {
        return !email.isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun isPassword(password: String?) : Boolean {
        return !password.isNullOrEmpty()
    }

    fun isPassword(password: String?, repeatedPassword: String?) : Boolean {
        return !password.isNullOrEmpty() && password.length >= 6 && password == repeatedPassword
    }
}