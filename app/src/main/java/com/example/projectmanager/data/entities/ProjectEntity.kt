package com.example.projectmanager.data.entities

data class ProjectEntity (
    val title: String? = null,
    val description: String? = null,
    val members: List<String>? =  null,
    val archived: Boolean? = null,
    val invite_qr_pic: String? = null,
    val code: String? = null,
    val created: String? = null
) {
    //constructor() : this("", "", "")
}