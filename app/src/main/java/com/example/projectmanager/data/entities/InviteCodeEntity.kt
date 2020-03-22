package com.example.projectmanager.data.entities

import java.io.Serializable

data class InviteCodeEntity (
    var id: String? = null,
    var projectId: String? = null,
    var password: String? = null
) : Serializable {
    override fun toString(): String = "${id}: ProjectId = ${projectId}"
}