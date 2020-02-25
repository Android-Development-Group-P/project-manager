package com.example.projectmanager.data.entities

import java.util.*

data class UserEntity (
    val id: String? = null,
    var displayName: String? = null,
    var email: String? = null,
    var projects: List<String>? = null,
    var createdAt: Date = Date()
) {
    override fun toString(): String = "id: ${id}: name: ${displayName} email: ${email}"
}