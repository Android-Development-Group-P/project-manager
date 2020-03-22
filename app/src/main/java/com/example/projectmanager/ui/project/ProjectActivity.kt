package com.example.projectmanager.ui.project

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.factories.JoinProjectViewModelFactory
import com.example.projectmanager.data.factories.ProjectViewModelFactory
import com.example.projectmanager.databinding.ActivityJoinProjectBinding
import com.example.projectmanager.ui.chat.ChatFragment
import com.example.projectmanager.ui.issue.CreateIssueFragment
import com.example.projectmanager.ui.issue.IssuesFragment
import com.example.projectmanager.util.QRGenerator
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_start.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class ProjectActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, KodeinAware {

    override val kodein by kodein()
    private val factory: ProjectViewModelFactory by instance()

    private lateinit var viewModel: ProjectViewModel

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

        viewModel = ViewModelProvider(this, factory).get(ProjectViewModel::class.java)
        setupViewModelActions()

        // Set the title for the activity
        title = project.title

        currentProject = project

        // Initialize the action bar
        nav_view.setNavigationItemSelectedListener(this)
        setupActionBar()

        if (savedInstanceState == null) {
            // Initialize the base fragment for the activity
            supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, IssuesFragment()
            ).commit()
        }
    }

    private fun setupViewModelActions() {
        viewModel.getEvent().observe(this, Observer {
            when (it) {
                is ProjectViewModel.Event.StartInviteDialog -> {
                    val fragment: InviteDialog = InviteDialog.newInstance(QRGenerator.QRObject(it.code.id, 512, 512))
                    fragment.show(supportFragmentManager, "")
                }
            }
        })
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
                    drawer_layout.openDrawer(GravityCompat.END)
                else
                    drawer_layout.closeDrawer(GravityCompat.END)

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
            } else {
                supportFragmentManager.popBackStack()
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, IssuesFragment()
                ).commit()
            }
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
                    R.id.nav_host_fragment_project, IssuesFragment()
                ).commit()
            }

            R.id.nav_chat -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, ChatFragment()
                ).commit()
            }
            R.id.nav_create_issue -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, CreateIssueFragment()
                ).commit()
            }

            R.id.nav_settings -> {

            }
            R.id.nav_members -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.nav_host_fragment_project, ProjectMembers()
                ).commit()
            }

            R.id.nav_invite_code -> {
                viewModel.startInviteDialog(currentProject!!.id!!)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.END)

        return true
    }
}
