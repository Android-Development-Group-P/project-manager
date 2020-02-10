package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectmanager.Auth.LoginActivity
import com.example.projectmanager.Managers.AuthenticationManager
import com.example.projectmanager.Managers.SessionManager
import com.example.projectmanager.Utilites.FirebaseAuthentication
import com.example.projectmanager.Utilites.FirebaseSession
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // DENNA SKA FLYTTAS https://stackoverflow.com/questions/7360846/how-can-i-execute-something-just-once-per-application-start
        AuthenticationManager.init(FirebaseAuthentication())
        SessionManager.init(FirebaseSession())

        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        button2.setOnClickListener {
            val intent = Intent(this, StartPageActivity::class.java)
            startActivity(intent)
        }
    }
}
