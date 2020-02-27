package com.example.projectmanager.ui.project

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.CreateProjectViewModelFactory
import com.example.projectmanager.data.factories.JoinProjectViewModelFactory
import com.example.projectmanager.databinding.ActivityCreateProjectBinding
import com.example.projectmanager.databinding.ActivityJoinProjectBinding
import com.example.projectmanager.ui.extra.ScanActivity
import com.example.projectmanager.util.toast
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_join_project.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class JoinProjectActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: JoinProjectViewModelFactory by instance()

    private lateinit var binding: ActivityJoinProjectBinding
    private lateinit var viewModel: JoinProjectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_project)

        viewModel = ViewModelProvider(this, factory).get(JoinProjectViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_join_project)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupViewModelActions()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupViewModelActions() {
        viewModel.getEvent().observe(this, Observer {
            when (it) {

                is JoinProjectViewModel.Event.Success -> {
                    toast("Success")
                    onActivityFinished()
                }

                is JoinProjectViewModel.Event.StartScanner -> {
                    IntentIntegrator(this).setCaptureActivity(ScanActivity::class.java).initiateScan()
                }

                is JoinProjectViewModel.Event.InviteCodeFound -> {
                    toast("Code found")
                }

                is JoinProjectViewModel.Event.ProjectLoaded -> {
                    toast("Project found")
                    project_container.visibility = View.VISIBLE
                }

                is JoinProjectViewModel.Event.Failure -> {
                    toast(it.error)
                    project_container.visibility = View.GONE
                }
            }
        })

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                setResult(Activity.RESULT_CANCELED)
                onActivityFinished()
            }
            R.id.create_project -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() = onActivityFinished()

    private fun onActivityFinished() {
        finish()
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
    }

    // Get the results:
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result.contents != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
            } else {
                toast(result.contents)
                viewModel.code.value = result.contents
                viewModel.search()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
