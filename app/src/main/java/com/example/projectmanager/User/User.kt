package com.example.projectmanager.User

open class User {

    private var userId: Int = 0
    private var username: String = ""

    constructor(userId: Int, username: String) {
        this.userId = userId
        this.username = username
    }

}
