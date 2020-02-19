package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IProjectRepository {

    fun create(project: ProjectEntity) : Single<String>

    fun getAll() : Single<List<ProjectEntity>>

    fun addUserToProject(projectId: String, userId: String) : Completable

    fun removerUserFromProject(projectId: String, userId: String) : Completable

    fun changeStatusOnProject(status: Boolean, projectId: String) : Completable

    fun checkIfCodeExists(code: String) : Single<Boolean>

}