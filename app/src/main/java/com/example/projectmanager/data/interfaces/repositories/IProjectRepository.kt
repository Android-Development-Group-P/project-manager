package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.ProjectEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProjectRepository {

    val listeners: Listener

    /**
     * Create a new project to the database
     *
     * @param project The project to save to the database
     * @return A "reactivex" "Single" object
     */
    fun create(project: ProjectEntity) : Single<String>

    /**
     * Update a project to the database
     *
     * @param project The project to update with
     * @return A "reactivex" "Completable" object
     */
    fun update(project: ProjectEntity) : Completable

    /**
     * Delete a project from the database
     *
     * @param id The ID of the project to delete
     * @return A "reactivex" "Completable" object
     */
    fun delete(id: String) : Completable

    /**
     * Retreive a project from the database by id
     *
     * @param id The ID of the project
     * @return A "reactivex" "Single" object
     */
    fun getById(id: String) : Single<ProjectEntity>

    /**
     * Retreive projects from the database by ids
     *
     * @param ids The IDS for several projects
     * @return A "reactivex" "Single" object
     */
    fun getByIdList(ids: List<String>): Single<List<ProjectEntity>>

    /**
     * Retreive all projects from the database
     *
     * @return A "reactivex" "Single" object
     */
    fun getAll() : Single<List<ProjectEntity>>

    interface Listener {

        /**
         * Retreive a project from the database by id, with listener
         *
         * @param id The ID of the project
         * @return A "reactivex" "Observable" object
         */
        fun getById(id: String) : Observable<ProjectEntity>
    }
}