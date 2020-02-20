package com.example.projectmanager

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.data.interfaces.IImageStorage
import com.example.projectmanager.data.interfaces.SessionProvider
import com.example.projectmanager.ui.auth.LoginActivity
import com.example.projectmanager.util.FirebaseFirestoreDB
import com.example.projectmanager.util.toast
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
