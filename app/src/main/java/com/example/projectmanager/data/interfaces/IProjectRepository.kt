package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IProjectRepository {

    fun create(project: ProjectEntity) : Single<String>

    fun update(project: ProjectEntity) : Completable

    fun delete(id: String) : Completable

    fun getById(id: String) : Single<ProjectEntity>

    fun getAll() : Single<List<ProjectEntity>>

    fun getSectionByIds(ids: List<String>) : Single<List<ProjectEntity>>

    fun checkIfCodeExists(code: String) : Single<Boolean>

}