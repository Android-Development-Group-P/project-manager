package com.example.projectmanager.ui.start

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.ui.createProject.StartNotificationFragment
import com.example.projectmanager.ui.createProject.StartProjectsFragment
import com.example.projectmanager.ui.settings.SettingsActivity
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        // Initialize the action bar
        nav_view.setNavigationItemSelectedListener(this)
        setupActionBar()

        if (savedInstanceState == null) {
            // Initialize the base fragment for the activity
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, StartProjectsFragment()
            ).commit()

            nav_view.setCheckedItem(R.id.nav_projects)
        }
    }

    /**
     * Setup what happens when user send a "BackPressed" event
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Setup the action bar on the top of the activity
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar)

        var toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        when (p0.itemId) {
            R.id.nav_projects -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, StartProjectsFragment()).commit()
            }

            R.id.nav_notifications -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, StartNotificationFragment()).commit()
            }

            R.id.nav_settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)

        return true
    }
}
