package com.example.projectmanager.data.services.firebase

import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.interfaces.repositories.IProjectRefRepository
import com.example.projectmanager.data.interfaces.repositories.IProjectRepository
import com.example.projectmanager.data.interfaces.services.IProjectService
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import java.lang.Exception

class FBProjectService (
    private val repository: IProjectRepository,
    private val references: IProjectRefRepository
) : IProjectService {

    override val listeners: IProjectService.IListener = Listener()

    override fun create(uid: String, project: ProjectEntity): Single<String> {
        return repository.create(project)
            .flatMap { id -> references.add(uid, id).toSingleDefault(id) }
    }

    override fun delete(id: String) = repository.delete(id)

    override fun getById(id: String) = repository.getById(id)

    override fun getSectionByList(ids: List<String>): Single<List<ProjectEntity>> {
        return Observable.fromIterable(ids)
            .flatMapSingle { id -> getById(id) }
            .toList()
    }

    override fun getSectionByUser(uid: String): Single<List<ProjectEntity>> {
        return references.getById(uid)
            .flatMap { ids -> getSectionByList(ids) }
    }

    override fun join(uid: String, projectId: String): Completable {
        return references.getById(uid)
            .flatMapCompletable {
                if (it.contains(projectId))
                    return@flatMapCompletable Completable.error(Exception("You've already joined this project."))

                references.add(uid, projectId)
            }
    }

    override fun leave(uid: String, projectId: String): Completable {
        return references.getById(uid)
            .flatMapCompletable {
                if (!it.contains(projectId))
                    return@flatMapCompletable Completable.error(Exception("You are not part of this project"))

                references.remove(uid, projectId)
            }
    }

    inner class Listener : IProjectService.IListener {

        override fun getSectionByUser(uid: String): Observable<List<ProjectEntity>> {
            return references.listener.getById(uid)
                .flatMapSingle { ids -> getSectionByList(ids) }
        }

    }
}