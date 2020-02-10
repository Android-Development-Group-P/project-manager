package com.example.projectmanager.Managers

import android.util.Log
import com.example.projectmanager.Interfaces.SessionProvider

object SessionManager {

    lateinit var session: SessionProvider

    /**
     * Initialize the singleton SessionManager with a
     * unique session provider
     * @param provider The authentication provider
     */
    fun init(provider: SessionProvider) {
        session = provider
    }

    fun createSession() {

    }
}