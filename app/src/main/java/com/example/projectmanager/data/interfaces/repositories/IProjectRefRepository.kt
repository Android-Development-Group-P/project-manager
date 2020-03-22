package com.example.projectmanager.data.interfaces.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IProjectRefRepository {

    val listener: IListener

    /**
     * Create a new project reference to the database
     *
     * @param uid The UID to accosiate the reference with
     * @return A "reactivex" "Single" object
     */
    fun create(uid: String) : Single<String>

    /**
     * Add a new UID to the reference
     *
     * @param uid The UID to accosiate the reference with
     * @param reference The reference
     * @return A "reactivex" "Complateable" object
     */
    fun add(uid: String, reference: String) : Completable

    /**
     * Remove a UID to the reference
     *
     * @param uid The UID to remove from reference
     * @param reference The reference
     * @return A "reactivex" "Complateable" object
     */
    fun remove(uid: String, reference: String) : Completable

    /**
     * Retrieve a project reference from the database
     *
     * @param uid The UID that is accosiated with the reference
     * @return A "reactivex" "Single" object
     */
    fun getById(uid: String) : Single<List<String>>

    interface IListener {

        /**
         * Retrieve a project reference from the database, with listener
         *
         * @param uid The UID that is accosiated with the reference
         * @return A "reactivex" "Observable" object
         */
        fun getById(uid: String): Observable<List<String>>
    }
}