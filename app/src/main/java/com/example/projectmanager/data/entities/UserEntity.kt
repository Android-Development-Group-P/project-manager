package com.example.projectmanager.data.entities

import java.util.*

data class UserEntity (
    val id: String,
    val name: String,
    val email: String,
    val projects: List<String>,
    val createdAt: Date
) {
    override fun toString(): String = name.toString()
}