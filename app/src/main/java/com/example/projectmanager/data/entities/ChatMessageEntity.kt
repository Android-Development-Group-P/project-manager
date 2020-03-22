package com.example.projectmanager.data.entities

import java.util.*


data class ChatMessageEntity (
    val sender: String? = null, val project: String? = null,
    val issue: String? = null, val message: String? = null,
    val createdAt: Date = Date()
)