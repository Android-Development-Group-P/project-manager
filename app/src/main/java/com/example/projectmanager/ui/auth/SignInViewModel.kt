package com.example.projectmanager.ui.auth

import androidx.lifecycle.ViewModel
import android.view.View
import com.example.projectmanager.data.managers.AuthenticationManager
import com.example.projectmanager.utilites.SingleLiveEvent
import com.example.projectmanager.utilites.Validation

class SignInViewModel : ViewModel() {

    var email: String = ""
    var password: String = ""

    var loginEvent = SingleLiveEvent<LoginEvent>()

    fun onLoginButtonClicked(view: View) {
        loginEvent.value = LoginEvent(LoginStatus.Started)

        if (Validation.isEmail(email) || password.isNullOrEmpty()) {
            loginEvent.value = LoginEvent(LoginStatus.Failure, "Invalid email or password")
            return
        }

        AuthenticationManager.auth.login(email, password) {
            if (it.isSuccessful) {
                loginEvent.value = LoginEvent(LoginStatus.Success)
            } else {
                loginEvent.value = LoginEvent(LoginStatus.Failure, it.error.toString())
            }
        }
    }


    data class LoginEvent(var status: LoginStatus, var error: String? = null)
    enum class LoginStatus
    {
        Started,
        Success,
        Failure
    }
}
