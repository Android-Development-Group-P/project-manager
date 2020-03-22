package com.example.projectmanager.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.projectmanager.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        // Setup the adapter that handles the "ViewPages"
        val adapter = EntryPagerAdapter(supportFragmentManager)
        adapter.fragments.add(LoginFragment())
        adapter.titles.add(getString(R.string.login_button))

        adapter.fragments.add(RegisterFragment())
        adapter.titles.add(getString(R.string.register_button))

        view_pager.adapter = adapter
        view_pager.setPagingEnabled(false)

        tab_layout.setupWithViewPager(view_pager)
    }
}


