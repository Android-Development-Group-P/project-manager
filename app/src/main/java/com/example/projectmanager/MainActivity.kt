package com.example.projectmanager

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.projectmanager.Managers.DatabaseManager
import com.example.projectmanager.ui.auth.LoginActivity
import com.example.projectmanager.ui.project_new.InviteDialog
import com.example.projectmanager.ui.start.StartActivity
import com.example.projectmanager.util.FirebaseFirestoreDB
import com.example.projectmanager.util.QRGenerator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DatabaseManager.init(FirebaseFirestoreDB())

        val ft = supportFragmentManager.beginTransaction()
        val newFragment = InviteDialog.newInstance(QRGenerator.QRObject("2193129038091283091820938091238908", 512, 512))
        newFragment.show(ft, "dialog")

        button.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        button2.setOnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        }
    }
}
