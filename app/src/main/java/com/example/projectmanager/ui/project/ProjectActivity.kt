package com.example.projectmanager.ui.project

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.ui.chat.ChatFragment
import com.example.projectmanager.ui.createProject.StartNotificationFragment
import com.example.projectmanager.ui.issue.CreateIssueFragment
import com.example.projectmanager.ui.issue.IssuesFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_start.*


class ProjectActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    companion object {
        const val PROJECT_EXTRA_ID = "Project"
        var currentProject: ProjectEntity? = null
        var members: List<UserEntity> = listOf()
    }

    private val project: ProjectEntity by lazy {
        intent.getSerializableExtra(PROJECT_EXTRA_ID) as ProjectEntity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        // Set the title for the activity
        title = project.title

        currentProject = project

        // Initialize the action bar
        nav_view.setNavigationItemSelectedListener(this)
        setupActionBar()


        supportFragmentManager.addOnBackStackChangedListener {
            Log.d("test123", "nein")
        }

        if (savedInstanceState == null) {
            // Initialize the base fragment for the activity
            supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, IssuesFragment()
            ).addToBackStack(null).commit()

            //nav_view.setCheckedItem(R.id.nav_issues)
        }
        //setupActionBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_project_option_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val count = supportFragmentManager.backStackEntryCount
                if (count == 0) {
                    setResult(Activity.RESULT_CANCELED)
                    finish()
                } else {
                    supportFragmentManager.popBackStack()
                    supportFragmentManager.beginTransaction().replace(
                        R.id.nav_host_fragment_project, IssuesFragment()
                    ).commit()
                }
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
            val count = supportFragmentManager.backStackEntryCount
            if (count == 0) {
                super.onBackPressed()
                //additional code
            } else {
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, IssuesFragment()
                ).commit()
            }
            //super.onBackPressed()
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
            /*
            R.id.nav_issues -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, IssuesFragment()
                ).commit()
            }

            R.id.nav_members -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.fragment_container, StartNotificationFragment()
                ).commit()
            }*/
            R.id.nav_issues -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, IssuesFragment()
                ).commit()
            }

            R.id.nav_chat -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, ChatFragment()
                ).commit()
                //nav_host_fragment_project.findNavController().navigate(R.id.action_nav_issues_to_nav_chat)
                //findNavController(R.id.nav_host_fragment_project).navigate(R.id.action_nav_issues_to_nav_chat)
            }
            R.id.nav_create_issue -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, CreateIssueFragment()
                ).commit()
                //nav_host_fragment_project.findNavController().navigate(R.id.action_nav_issues_to_nav_create_issue)
                //findNavController(R.id.nav_host_fragment_project).navigate(R.id.action_nav_issues_to_nav_create_issue)
            }

            R.id.nav_settings -> {
                Log.d("test123", "sgghdhgdfsghfgd")
            }
            R.id.nav_members -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, ProjectMembers()
                ).commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.END)

        return true
    }
}
