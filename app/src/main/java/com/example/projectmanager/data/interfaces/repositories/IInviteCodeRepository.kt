package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.InviteCodeEntity
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

interface IInviteCodeRepository {

    /**
     * Create a new invite code to the database
     *
     * @param code The invite code to save to the database
     * @return A "reactivex" "Single" object
     */
    fun create(code: InviteCodeEntity): Single<String>

    /**
     * Delete a invite code from the database
     *
     * @param id The ID of the invite code
     * @return A "reactivex" "Completable" object
     */
    fun delete(id: String): Completable

    /**
     * Retrieve a invite code from a specific ID
     *
     * @param id The ID of the invite code
     * @return A "reactivex" "Single" object
     */
    fun getById(id: String) : Single<InviteCodeEntity>

    /**
     * Retreive a invite code where something equals a field.
     *
     * @param field The equal field
     * @param value The value to check if equal
     * @return A "reactivex" "Single" object
     */
    fun getWhereEqual(field: String, value: String) : Single<InviteCodeEntity>
}