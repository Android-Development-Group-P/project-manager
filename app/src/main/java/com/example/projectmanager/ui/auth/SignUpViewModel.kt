package com.example.projectmanager.ui.auth

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.projectmanager.data.managers.AuthenticationManager
import com.example.projectmanager.utilites.SingleLiveEvent
import com.example.projectmanager.utilites.Validation

class SignUpViewModel : ViewModel() {

    var username: String = ""
    var email: String = ""
    var password: String = ""
    var repeatedPassword = ""

    var registerEvent = SingleLiveEvent<RegisterEvent>()

    fun onRegisterButtonClicked(view: View) {
        registerEvent.value = RegisterEvent(RegisterStatus.Started)

        if (Validation.isUsername(username)) {
            registerEvent.value = RegisterEvent(RegisterStatus.Failure, "Incorrent username")
            return
        }

        if (Validation.isEmail(email) || Validation.isPassword(password, repeatedPassword)) {
            registerEvent.value = RegisterEvent(RegisterStatus.Failure, "Invalid email or password")
            return
        }

        AuthenticationManager.auth.register(username, email, password) {
            if (it.isSuccessful) {
                registerEvent.value = RegisterEvent(RegisterStatus.Success)
            } else {
                registerEvent.value = RegisterEvent(RegisterStatus.Failure, it.error.toString())
            }
        }
    }

    data class RegisterEvent(var status: RegisterStatus, var error: String? = null)
    enum class RegisterStatus
    {
        Started,
        Success,
        Failure
    }
}