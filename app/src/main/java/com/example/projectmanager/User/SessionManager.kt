package com.example.projectmanager.User

class SessionManager {

    companion object {

        val instance = SessionManager()
        private var currentUser: User? = null

    }

}
