package com.example.projectmanager.Managers

import com.example.projectmanager.Interfaces.DatabaseProvider

object DatabaseManager {

    lateinit var db: DatabaseProvider

    /**
     * Initialize the singleton AuthenticationManager with a
     * unique authentication provider
     * @param provider The authentication provider
     */
    fun init(provider: DatabaseProvider) {
        db = provider
    }

}