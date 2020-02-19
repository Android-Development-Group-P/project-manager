package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IProjectRepository {

    fun create(project: ProjectEntity) : Completable

    fun getAll() : Single<List<ProjectEntity>>
}