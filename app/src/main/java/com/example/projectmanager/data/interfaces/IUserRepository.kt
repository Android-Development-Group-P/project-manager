package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IUserRepository {

    /**
     * Login the account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Completeable" object
     */
    fun login(email: String, password: String) : Completable

    /**
     * Register a new account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Completeable" object
     */
    fun register(email: String, password: String) : Completable

    /**
     * Logout the account to the application
     *
     * @return A "reactivex" "Completeable" object
     */
    fun logout() : Completable

    fun getCurrentUser() : Single<UserEntity>
}