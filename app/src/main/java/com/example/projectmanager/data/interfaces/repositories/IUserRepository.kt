package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IUserRepository {

    val listeners: IListener

    /**
     * Create a new user to the database
     *
     * @param user The user to save to the database
     * @return A "reactivex" "Single" object
     */
    fun create(user: UserEntity) : Single<String>

    /**
     * Update a user to the database
     *
     * @param user The user to update with
     * @return A "reactivex" "Completable" object
     */
    fun update(user: UserEntity) : Completable

    /**
     * Delete a user from the database
     *
     * @param id The ID of the user to delete
     * @return A "reactivex" "Completable" object
     */
    fun delete(id: String) : Completable

    /**
     * Retreive a user from the database by id
     *
     * @param id The ID of the user
     * @return A "reactivex" "Single" object
     */
    fun getById(id: String) : Single<UserEntity>

    /**
     * Retreive all users from the database
     *
     * @return A "reactivex" "Single" object
     */
    fun getAll(): Single<List<UserEntity>>

    /**
     * Retreive users from the database by ids
     *
     * @param ids The IDS for several users
     * @return A "reactivex" "Single" object
     */
    fun getAllById(ids: List<String>) : Single<List<UserEntity>>

    interface IListener {

        /**
         * Retreive a user from the database by id, with listener
         *
         * @param id The ID of the user
         * @return A "reactivex" "Observable" object
         */
        fun getById(id: String): Observable<UserEntity>
    }
}