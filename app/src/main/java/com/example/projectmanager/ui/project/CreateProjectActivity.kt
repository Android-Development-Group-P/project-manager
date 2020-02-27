package com.example.projectmanager.ui.project

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.CreateProjectViewModelFactory
import com.example.projectmanager.databinding.ActivityCreateProjectBinding
import com.example.projectmanager.util.toast
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class CreateProjectActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: CreateProjectViewModelFactory by instance()

    private lateinit var binding: ActivityCreateProjectBinding
    private lateinit var viewModel: CreateProjectViewModel

    private var createProjectItem: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

        viewModel = ViewModelProvider(this, factory).get(CreateProjectViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_project)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupViewModelActions()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_create_project, menu)
        createProjectItem = menu?.findItem(R.id.create_project)
        createProjectItem?.isEnabled = false
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                onActivityFinished()
            }
            R.id.create_project -> {
                viewModel.create()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = onActivityFinished()

    private fun setupViewModelActions() {
        viewModel.getFormValidation().observe(this, Observer {
            createProjectItem?.isEnabled = it
        })

        viewModel.getEvent().observe(this, Observer {
            when (it) {
                is CreateProjectViewModel.Event.Started -> { toast( "Started" )}
                is CreateProjectViewModel.Event.Success -> {
                    setResult(Activity.RESULT_OK)
                    onActivityFinished()
                }
                is CreateProjectViewModel.Event.Failure -> { toast(it.error) }
            }
        })
    }

    private fun onActivityFinished() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }
}
