package com.example.projectmanager.data.entities

import com.google.firebase.firestore.Exclude
import org.threeten.bp.LocalDateTime
import java.io.Serializable
import java.util.*

data class InviteCodeEntity (
    var id: String? = null,
    var projectId: String? = null,
    var password: String? = null
) : Serializable {
    override fun toString(): String = "${id}: ProjectId = ${projectId}"
}