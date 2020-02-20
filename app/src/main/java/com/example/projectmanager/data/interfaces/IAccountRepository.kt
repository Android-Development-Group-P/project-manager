package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.entities.UserEntity
import io.reactivex.Completable
import io.reactivex.Single

interface IAccountRepository {

    /**
     * Login the account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Completeable" object
     */
    fun login(email: String, password: String) : Single<String>

    /**
     * Register a new account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Completeable" object
     */
    fun register(email: String, password: String) : Single<String>
/*
    /**
     * Logout the account to the application
     *
     * @return A "reactivex" "Completeable" object
     */
    fun logout() : Completable*/
}