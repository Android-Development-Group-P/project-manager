package com.example.projectmanager.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.projectmanager.R
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.factories.HomeViewModelFactory
import com.example.projectmanager.ui.issue.CreateIssueFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance


class HomeFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = HomeFragment()

        lateinit var projectId : String
    }

    override val kodein by kodein()
    private val factory : HomeViewModelFactory by instance()

    private lateinit var viewModel: HomeViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        recyclerView = root.findViewById(R.id.recyclerView_home)

        recyclerView.layoutManager = LinearLayoutManager(context)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel

        viewModel.loadAllIssues(projectId)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                HomeViewModel.HomeStatus.started -> onStarted()
                HomeViewModel.HomeStatus.Success -> onSuccess(it.issues!!)
                HomeViewModel.HomeStatus.Failure -> onFailure(it.error!!)
            }
        })

        view?.findViewById<FloatingActionButton>(R.id.floatingActionButton)?.setOnClickListener {view ->
            CreateIssueFragment.projectId = projectId
            view.findNavController().navigate(R.id.action_nav_home_to_nav_create_issue)
        }
    }

    private fun onStarted() {
        Toast.makeText(context, "Started", Toast.LENGTH_SHORT).show()
    }

    private fun onSuccess(issues: List<IssueEntity>) {

        if (issues.size == 0) {
            Toast.makeText(context, "No issues", Toast.LENGTH_SHORT).show()
        } else {
            val adapter = HomeAdapter(issues)

            recyclerView.adapter = adapter

            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}