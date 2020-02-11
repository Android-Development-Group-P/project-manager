package com.example.projectmanager.Models

class UserFirebase(
    val projects: List<String>? = null, val username: String? = null,
    val email: String? = null, val created_issues: List<String>? = null,
    val assigned_issues: List<String>? = null, created: String? = null)