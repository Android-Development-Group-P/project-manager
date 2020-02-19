package com.example.projectmanager.data.entities

data class UserEntity (
    val uid: String,
    val name: String,
    val email: String
) {
    override fun toString(): String = name.toString()
}