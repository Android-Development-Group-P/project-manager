package com.example.projectmanager.data.entities

data class ProjectEntity (
    val id: String,
    val name: String,
    val code: String
) {
    constructor() : this("", "", "")
}