package com.example.projectmanager.data.entities

import com.google.firebase.firestore.Exclude
import org.threeten.bp.LocalDateTime
import java.io.Serializable
import java.util.*

data class ProjectEntity (
    @get:Exclude
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var members: List<String>? =  null,
    var archived: Boolean? = false,
    var password: String? = null,
    var createdAt: Date? = Date(),
    var updatedAt: LocalDateTime? = null
) : Serializable {
    override fun toString(): String = "${id}: ${title} - Created at: ${createdAt}"
}