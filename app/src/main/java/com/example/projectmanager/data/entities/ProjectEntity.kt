package com.example.projectmanager.data.entities

data class ProjectEntity (
    var id: String? = null,
    var title: String? = null,
    var description: String? = null,
    var members: List<String>? =  null,
    var archived: Boolean? = null,
    var invite_qr_pic: String? = null,
    var code: String? = null,
    var created: String? = null
) {
    //constructor() : this("", "", "")
}