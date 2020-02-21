package com.example.projectmanager.data.entities

data class ChatMessageEntity (
    val sender: String? = null, val project: String? = null,
    val issue: String? = null, val message: String? = null
)