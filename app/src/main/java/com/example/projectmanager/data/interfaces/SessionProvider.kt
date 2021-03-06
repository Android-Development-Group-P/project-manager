package com.example.projectmanager.data.interfaces

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import com.example.projectmanager.MainActivity
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.interfaces.services.IUserService
import com.google.firebase.firestore.auth.User
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

abstract class SessionProvider {

    private val userService: IUserService

    private val PRIVATE_MODE = 0

    private var preferences: SharedPreferences
    private var editor: SharedPreferences.Editor
    private var context: Context

    private val KEY_PREFERENCE = "SESSION"
    private val KEY_IS_LOGGED_IN = "IS_LOGGED_IN"
    private val KEY_USER = "USER_ENTITY"

    private var isListening = false

    private var disposable: Disposable? = null

    var user: UserEntity? = null
        get() = Gson().fromJson(preferences.getString(KEY_USER, null), UserEntity::class.java)

    /**
     * Initialize the singleton SessionManager with the application context
     * @param context The 'Context'
     */
     constructor(context: Context, userService: IUserService) {
        this.context = context
        this.userService = userService
        preferences = context.getSharedPreferences(KEY_PREFERENCE, PRIVATE_MODE)
        editor = preferences.edit()
    }

    /**
     * Start the listener that fetches information
     * about current user in real time
     */
    fun startListener() {
        disposable = userService.listeners.getById(user!!.id!!)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("Session", "Retrieved new update for user in sessionn.")
                createSession(it)
            }, {

            })

        isListening = true
    }

    /**
     * Stop the listener that fetches information
     * about current user
     */
    fun stopListener() {
        disposable?.dispose()
        isListening = false
    }

    /**
     * Create a new session with the specific 'User' details
     * @param user The 'UserEntity' object
     */
    fun createSession(user: UserEntity) {
        editor.putBoolean(KEY_IS_LOGGED_IN, true)
        editor.putString(KEY_USER, Gson().toJson(user))
        editor.commit()

        if (!isListening)
            startListener()

        Log.d("SessionTag", "Null or " + user.toString())
    }

    /**
     * Remove the current session
     */
    fun removeSession() {
        stopListener()

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