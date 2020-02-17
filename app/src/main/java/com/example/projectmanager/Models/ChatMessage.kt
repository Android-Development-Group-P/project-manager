package com.example.projectmanager.Models

data class ChatMessage(
    val sender: String? = null, val project: String? = null,
    val issue: String? = null, val message: String? = null
)