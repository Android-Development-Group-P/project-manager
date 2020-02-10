package com.example.projectmanager.Interfaces

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
    fun login(email: String, password: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /**
     * Register a new account to the application
     *
     * @param email The email for the specific account
     * @param password The password for the specific account
     * @param callback A callback with result of the authentication result
     */
    fun register(email: String, password: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /**
     * Logout the account to the application
     *
     * @param callback A callback with result of the authentication result
     */
    fun logout(callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /**
     * Resets the password on the account
     *
     * @param email The email for the specific account
     * @param callback A callback with result of the authentication result
     */
    fun resetPassword(email: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)

    /**
     * Change the password on the account
     *
     * @param newPassword The new password to be set
     * @param callback A callback with result of the authentication result
     */
    fun changePassword(newPassword: String, callback: (isSuccessful: Boolean, error: String?) -> Unit)
}