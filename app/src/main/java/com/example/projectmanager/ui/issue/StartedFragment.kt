package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import com.example.projectmanager.ui.project.ProjectActivity
import kotlinx.android.synthetic.main.issues_fragment.*
import kotlinx.android.synthetic.main.issues_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class StartedFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = StartedFragment()
    }

    override val kodein by kodein()
    private val factory : IssuesViewModelFactory by instance()

    private lateinit var viewModel: IssuesViewModel
    lateinit var adapter: IssuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.issues_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(IssuesViewModel::class.java)

        viewModel.projectId = ProjectActivity.currentProject?.id!!
        viewModel.status = "Started"

        viewModel.initFun()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = IssuesAdapter(listOf())
        recyclerView.adapter = adapter
        viewModel.getIssues().observe(viewLifecycleOwner, Observer {
            adapter.setList(it.data!!)
            swipeLayout.isRefreshing = false
        })

        swipeLayout.setOnRefreshListener {
            viewModel.loadIssues()
        }
    }
}