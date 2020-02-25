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

        var adapter = EntryPagerAdapter(supportFragmentManager)
        adapter.fragments.add(LoginFragment())
        adapter.titles.add("Sign In")

        adapter.fragments.add(RegisterFragment())
        adapter.titles.add("Register")

        view_pager.adapter = adapter
        view_pager.setPagingEnabled(false)

        tab_layout.setupWithViewPager(view_pager)
    }
}


