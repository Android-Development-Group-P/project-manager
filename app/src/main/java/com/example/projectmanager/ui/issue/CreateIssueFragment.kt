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
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.data.factories.CreateIssueViewModelFactory
import com.example.projectmanager.databinding.CreateIssueFragmentBinding
import com.example.projectmanager.ui.project_new.ProjectActivity
import kotlinx.android.synthetic.main.create_issue_fragment.*
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

        viewModel = ViewModelProvider(this, factory).get(CreateIssueViewModel::class.java)

        viewModel.setProjectId(ProjectActivity.currentProject?.id!!)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                CreateIssueViewModel.IssueStatus.Success -> onSuccess()
                CreateIssueViewModel.IssueStatus.Failure -> onFailure(it.error!!)
            }
        })

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            priorityDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            assignToUserDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.label_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            labelDropdown.adapter = adapter
        }

        viewModel.label = labelDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String

        binding.viewModel = viewModel

    }

    private fun onSuccess() {
        Toast.makeText(context, "Success creating the issue", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_nav_create_issue_to_nav_issues)
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.label = labelDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.label = labelDropdown.selectedItem as String
        viewModel.priority = priorityDropdown.selectedItem as String
        viewModel.assignedUser = assignToUserDropdown.selectedItem as String
    }
}

