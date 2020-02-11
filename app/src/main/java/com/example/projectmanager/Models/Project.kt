package com.example.projectmanager.Models

data class Project(
    val members: List<String>? =  null, val issues: List<String>? = null,
    val archived: Boolean? = null, val invite_qr_pic: String? = null,
    val code: String? = null
)