package com.example.projectmanager.Models

data class Project (
    val title: String? = null,
    val description: String? = null,
    val members: List<String>? =  null,
    val archived: Boolean? = null,
    val invite_qr_pic: String? = null,
    val code: String? = null,
    val created: String? = null
)