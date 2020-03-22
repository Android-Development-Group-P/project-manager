package com.example.projectmanager.data.interfaces.repositories

import com.example.projectmanager.data.entities.UserEntity
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import io.reactivex.Completable
import io.reactivex.Single

interface IAccountRepository {

    /**
     * Login the account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Single" object
     */
    fun login(email: String, password: String) : Single<String>

    /**
     * Login with a google account to the application
     *
     * @return A "reactivex" "Single" object
     */
    fun loginWithGoogleSignIn(account: GoogleSignInAccount) : Single<String>

    /**
     * Register a new account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @return A "reactivex" "Single" object
     */
    fun register(email: String, password: String) : Single<String>
}