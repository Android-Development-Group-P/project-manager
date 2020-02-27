package com.example.projectmanager.data.interfaces.services

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProjectService {

    val listeners: IListener

    fun create(uid: String, project: ProjectEntity): Single<String>
    fun delete(id: String): Completable
    fun getById(id: String): Single<ProjectEntity>
    fun getSectionByList(ids: List<String>): Single<List<ProjectEntity>>
    fun getSectionByUser(uid: String): Single<List<ProjectEntity>>
    fun join(uid: String, projectId: String): Completable
    fun leave(uid: String, projectId: String): Completable

    interface IListener {
        fun getSectionByUser(uid: String): Observable<List<ProjectEntity>>
    }
}