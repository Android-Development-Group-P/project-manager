package com.example.projectmanager.ui.project

import android.app.Activity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.ui.createProject.StartNotificationFragment
import com.example.projectmanager.ui.issue.IssuesFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_start.*

class ProjectActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val PROJECT_EXTRA_ID = "Project"
    }

    private val project: ProjectEntity by lazy {
        intent.getSerializableExtra(PROJECT_EXTRA_ID) as ProjectEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        // Set the title for the activity
        title = project.title

        // Initialize the action bar
        setupActionBar()

        if (savedInstanceState == null) {
            // Initialize the base fragment for the activity
            supportFragmentManager.beginTransaction().replace(
                R.id.fragment_container, IssuesFragment()
            ).commit()

            nav_view.setCheckedItem(R.id.nav_issues)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_project_option_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                finish()
            }

            R.id.open_drawer -> {
                if(!drawer_layout.isDrawerOpen(GravityCompat.END))
                    drawer_layout.openDrawer(GravityCompat.END);
                else
                    drawer_layout.closeDrawer(GravityCompat.END);

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    /**
     * Setup what happens when user send a "BackPressed" event
     */
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.END)) {
            drawer_layout.closeDrawer(GravityCompat.END)
        } else {
            super.onBackPressed()
        }
    }

    /**
     * Setup the action bar on the top of the activity
     */
    private fun setupActionBar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        when (p0.itemId) {
            R.id.nav_issues -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, IssuesFragment()
                ).commit()
            }

            R.id.nav_members -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, StartNotificationFragment()
                ).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.END)

        return true
    }
}
