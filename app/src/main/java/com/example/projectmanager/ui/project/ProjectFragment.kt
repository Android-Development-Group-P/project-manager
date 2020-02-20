package com.example.projectmanager.ui.project

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.data.factories.ProjectViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProjectFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    override val kodein by kodein()
    private val factory : ProjectViewModelFactory by instance()

    private lateinit var viewModel: ProjectViewModel
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.project_fragment, container, false)

        recyclerView = root.findViewById(R.id.recyclerView_projects)

        val adapter = ProjectAdapter(listOf())

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        return root
    }
 
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(ProjectViewModel::class.java)

        // TODO: get project ids from logged in user
        viewModel.loadAllProjects(listOf("2cZymB2j4xB1rOqEgi5s", "F0clzNnpenHYqQ844ycR", "XCX6nER1XMXc14x0A7ml"))


        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ProjectViewModel.ProjectStatus.started -> onStarted()
                ProjectViewModel.ProjectStatus.Success -> onSuccess(it.projects!!)
                ProjectViewModel.ProjectStatus.Failure -> onFailure(it.error!!)
            }
        })

    }


    private fun onStarted() {
        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()
    }

    private fun onSuccess(projects: List<ProjectEntity>) {
        val adapter = ProjectAdapter((projects))

        recyclerView.adapter = adapter

        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}
