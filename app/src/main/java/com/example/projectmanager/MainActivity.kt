package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.projectmanager.ui.auth.LoginActivity
import com.example.projectmanager.data.managers.AuthenticationManager
import com.example.projectmanager.data.managers.SessionManager
import com.example.projectmanager.data.providers.FirebaseAuthentication
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DENNA SKA FLYTTAS https://stackoverflow.com/questions/7360846/how-can-i-execute-something-just-once-per-application-start
        AuthenticationManager.init(FirebaseAuthentication())
        SessionManager.init(this)

        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        button2.setOnClickListener {
            val intent = Intent(this, StartPageActivity::class.java)
            startActivity(intent)
        }
    }
}
