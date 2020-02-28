package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProjectRepository {

    val listeners: Listener

    fun create(project: ProjectEntity) : Single<String>
    fun update(project: ProjectEntity) : Completable
    fun delete(id: String) : Completable

    fun getById(id: String) : Single<ProjectEntity>

    fun getAll() : Single<List<ProjectEntity>>

    interface Listener {
        fun getById(id: String) : Observable<ProjectEntity>
    }
}