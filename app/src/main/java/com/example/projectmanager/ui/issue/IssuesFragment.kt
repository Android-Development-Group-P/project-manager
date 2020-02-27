package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import com.example.projectmanager.ui.project_new.ProjectActivity
import kotlinx.android.synthetic.main.fragment_issues.*
import kotlinx.android.synthetic.main.fragment_start_projects.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class IssuesFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = IssuesFragment()

        lateinit var adapter: IssuesAdapter
    }

    override val kodein by kodein()
    private val factory: IssuesViewModelFactory by instance()

    private lateinit var viewModel: IssuesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_issues, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(IssuesViewModel::class.java)

        viewModel.projectId = ProjectActivity.currentProject?.id!!

        viewModel.initFun()

        recyclerView_issues.layoutManager = LinearLayoutManager(activity)
        adapter = IssuesAdapter(listOf())
        recyclerView_issues.adapter = adapter
        viewModel.getIssues().observe(viewLifecycleOwner, Observer {
            adapter.setList(it.data!!)
            swipeLayoutIssues.isRefreshing = false
        })

        swipeLayoutIssues.setOnRefreshListener {
            viewModel.loadIssues()
        }

        addIssueButton.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_nav_issues_to_nav_create_issue)
        }

        toChatButton.setOnClickListener {view ->
            Log.d("1234", "asd")
            view.findNavController().navigate(R.id.action_nav_issues_to_nav_chat)
        }
    }
}
