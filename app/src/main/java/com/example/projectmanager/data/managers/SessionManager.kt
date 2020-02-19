package com.example.projectmanager.data.managers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.projectmanager.MainActivity
import com.example.projectmanager.data.entities.UserEntity

object SessionManager {

    private val PRIVATE_MODE = 0

    private lateinit var preferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var context: Context

    private val KEY_PREFERENCE = "SESSION"
    private val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    private val KEY_UID = "UID"
    private val KEY_NAME = "NAME"
    private val KEY_EMAIL = "EMAIL"

    /**
     * Initialize the singleton SessionManager with the application context
     * @param context The 'Context'
     */
    fun init(context: Context) {
        this.context = context
        preferences = context.getSharedPreferences(KEY_PREFERENCE, PRIVATE_MODE)
        editor = preferences.edit()
    }

    /**
     * Create a new session with the specific 'User' details
     * @param user The 'UserEntity' object
     */
    fun createSession(user: UserEntity) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        Log.d("user", user.uid.toString())
        editor.putString(KEY_UID, user.uid)
        editor.putString(KEY_NAME, user.name)
        editor.putString(KEY_EMAIL, user.email)
        editor.commit()
    }

    /**
     * Remove the current session
     */
    fun removeSession() {
        editor.clear()
        editor.commit()

        val intent = Intent(context, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        context.startActivity(intent)
    }
/*
    /**
     * Retrieve the 'User' details from the current session
     * @return The 'User' object
     */
    fun getUserDetails() : UserEntity = UserEntity(
        preferences.getString(KEY_UID, ""),
        preferences.getString(KEY_NAME, ""),
        preferences.getString(KEY_EMAIL, ""))*/

    /**
     * Check if the current user is logged in
     */
    fun isLoggedIn() : Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}