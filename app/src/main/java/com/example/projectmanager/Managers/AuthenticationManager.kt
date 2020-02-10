package com.example.projectmanager.Managers

import com.example.projectmanager.Interfaces.AuthenticationProvider
import com.example.projectmanager.Interfaces.SessionProvider

object AuthenticationManager {

    lateinit var auth: AuthenticationProvider

    /**
     * Initialize the singleton AuthenticationManager with a
     * unique authentication provider
     * @param provider The authentication provider
     */
    fun init(provider: AuthenticationProvider) {
        auth = provider
    }
}