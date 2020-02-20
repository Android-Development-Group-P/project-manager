package com.example.projectmanager.data.interfaces

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import com.example.projectmanager.MainActivity
import com.example.projectmanager.data.entities.UserEntity
import com.google.gson.Gson

abstract class SessionProvider {

    private val PRIVATE_MODE = 0

    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var context: Context

    private val KEY_PREFERENCE = "SESSION"
    private val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    private val KEY_USER = "USER_ENTITY"

    var user: UserEntity? = null
        get() = Gson().fromJson(preferences.getString(KEY_USER, null), UserEntity::class.java)

    /**
     * Initialize the singleton SessionManager with the application context
     * @param context The 'Context'
     */
     constructor(context: Context) {
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
        editor.putString(KEY_USER, Gson().toJson(user))
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

    /**
     * Check if the current user is logged in
     */
    fun isLoggedIn() : Boolean {
        return preferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
}