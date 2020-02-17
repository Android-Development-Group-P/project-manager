package com.example.projectmanager.data.managers

import com.example.projectmanager.data.interfaces.AuthenticationProvider

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