package com.example.projectmanager

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.data.factories.MainViewModelFactory
import com.example.projectmanager.ui.auth.LoginActivity
import com.example.projectmanager.ui.extra.ScanActivity
import com.example.projectmanager.ui.start.StartActivity
import com.example.projectmanager.util.toast
import com.google.zxing.integration.android.IntentIntegrator
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class MainActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: MainViewModelFactory by instance()

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.isLoggedIn().observe(this, Observer {
            when (it) {
                true -> {
                    val intent = Intent(this, StartActivity::class.java)
                    startActivity(intent)
                    finish()
                }

                false -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        })
    }
}
