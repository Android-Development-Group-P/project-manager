package com.example.projectmanager.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.projectmanager.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        var adapter = EntryPagerAdapter(supportFragmentManager)
        adapter.fragments.add(SignInFragment())
        adapter.titles.add("Sign In")

        adapter.fragments.add(SignUpFragment())
        adapter.titles.add("Register")

        view_pager.adapter = adapter
        view_pager.setPagingEnabled(false)

        tab_layout.setupWithViewPager(view_pager)
    }
}


