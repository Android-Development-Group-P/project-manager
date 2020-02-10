package com.example.projectmanager.Auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.projectmanager.R
import com.example.projectmanager.Managers.AuthenticationManager
import com.example.projectmanager.Managers.SessionManager
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    companion object {
        const val LOG_TAG = "Authentication"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        resetPasswordButton.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener(this::onLoginClicked)
    }

    fun onLoginClicked(view: View) {
        var email = emailTextFieldLogin.text.toString()
        var password = passwordTextFieldLogin.text.toString()

        AuthenticationManager.auth.login(email, password) { isSuccessful, error ->
            if (isSuccessful) {
                SessionManager.createSession()
            } else {
                Log.d(LOG_TAG, error)
            }
        }
    }
}


