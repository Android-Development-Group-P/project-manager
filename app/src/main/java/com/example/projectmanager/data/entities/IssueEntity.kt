package com.example.projectmanager.data.entities

data class IssueEntity(
    var id: String? = null,
    val creator: String? = null,
    val created: String? = null,
    val title: String? = null,
    val description: String? = null,
    val priority: String? = null,
    val assigned_user: String? = null,
    val label: String? = null,
    val area: String? = null,
    val status: String? = null,
    val project: String? = null
)