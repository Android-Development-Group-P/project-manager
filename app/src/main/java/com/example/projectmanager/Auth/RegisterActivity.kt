package com.example.projectmanager.Auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projectmanager.R
import com.example.projectmanager.Interfaces.AuthenticationProvider
import com.example.projectmanager.Managers.AuthenticationManager
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "Authentication"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerButton.setOnClickListener(this::onRegisterClicked)
    }

    fun onRegisterClicked(view: View) {
        val email = emailTextFieldRegister.text.toString()
        val password = passwordTextFieldRegister.text.toString()

        AuthenticationManager.auth.register(email, password) { isSuccessful, error ->
            if (isSuccessful) {

            } else {
                Log.d(LOG_TAG, error)
            }
        }
    }
}
