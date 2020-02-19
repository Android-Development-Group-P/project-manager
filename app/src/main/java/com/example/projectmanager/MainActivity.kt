package com.example.projectmanager

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectmanager.ui.auth.LoginActivity
import com.example.projectmanager.data.managers.SessionManager
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.Utilites.FirebaseFirestoreDB

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SessionManager.init(this)
        DatabaseManager.init(FirebaseFirestoreDB())

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
