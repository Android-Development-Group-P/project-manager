package com.example.projectmanager.data.interfaces

import com.example.projectmanager.data.network.AuthResponse

interface AuthenticationProvider {

    companion object {
        val LOG_TAG = "Authentication"
    }

    /**
     * Login the account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @param callback A callback with result of the authentication result
     */
    fun login(email: String, password: String, callback: (result: AuthResponse) -> Unit)

    /**
     * Register a new account to the application
     *
     * @param username The username for the specific account
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @param callback A callback with result of the authentication result
     */
    fun register(username: String, email: String, password: String, callback: (result: AuthResponse) -> Unit)

    /**
     * Logout the account to the application
     *
     * @param callback A callback with result of the authentication result
     */
    fun logout(callback: (result: AuthResponse) -> Unit)

    /**
     * Resets the password on the account
     *
     * @param email The email for the specific account
     * @param callback A callback with result of the authentication result
     */
    fun resetPassword(email: String, callback: (result: AuthResponse) -> Unit)

    /**
     * Change the password on the account
     *
     * @param newPassword The new password to be set
     * @param callback A callback with result of the authentication result
     */
    fun changePassword(newPassword: String, callback: (result: AuthResponse) -> Unit)
}