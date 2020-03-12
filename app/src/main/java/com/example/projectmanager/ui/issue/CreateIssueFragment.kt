package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.UserEntity
import com.example.projectmanager.data.factories.CreateIssueViewModelFactory
import com.example.projectmanager.databinding.CreateIssueFragmentBinding
import kotlinx.android.synthetic.main.create_issue_fragment.*
import com.example.projectmanager.ui.project.ProjectActivity
import kotlinx.android.synthetic.main.activity_project.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class CreateIssueFragment : Fragment(), KodeinAware, AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = CreateIssueFragment()

        lateinit var projectId : String
    }

    override val kodein by kodein()
    private val factory : CreateIssueViewModelFactory by instance()

    private lateinit var binding: CreateIssueFragmentBinding
    private lateinit var viewModel: CreateIssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_issue_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }

        viewModel = ViewModelProvider(this, factory).get(CreateIssueViewModel::class.java)

        viewModel.setProjectId(ProjectActivity.currentProject?.id!!)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                CreateIssueViewModel.IssueStatus.Success -> onSuccess()
                CreateIssueViewModel.IssueStatus.Failure -> onFailure(it.error!!)
                CreateIssueViewModel.IssueStatus.Started -> onStarted()
            }
        })

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            priorityDropdown.adapter = adapter
        }

        val list = mutableListOf<String>()
        list.add("Anyone")
        for (member in ProjectActivity.members) {
            list.add(member.email ?: "Anyone")
        }
        val adapterUsers = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, list)
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        assignToUserDropdown.adapter = adapterUsers

        ArrayAdapter.createFromResource(
            context!!,
            R.array.label_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            departmentDropdown.adapter = adapter
        }

        viewModel.department = departmentDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String

        departmentDropdown.onItemSelectedListener = this
        assignToUserDropdown.onItemSelectedListener = this
        priorityDropdown.onItemSelectedListener = this

        binding.viewModel = viewModel

    }

    private fun onStarted() {
        activity?.runOnUiThread {
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
        }
    }

    private fun onSuccess() {
        Toast.makeText(context, "Success creating the issue", Toast.LENGTH_SHORT).show()
        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }
        (context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment_project, IssuesFragment()
        ).commit()
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.department = departmentDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.department = departmentDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String
    }

}

