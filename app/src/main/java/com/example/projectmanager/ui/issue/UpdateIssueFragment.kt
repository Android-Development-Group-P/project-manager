package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.factories.UpdateIssueViewModelFactory
import com.example.projectmanager.databinding.UpdateIssueFragmentBinding
import kotlinx.android.synthetic.main.update_issue_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class UpdateIssueFragment : Fragment(), KodeinAware, AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = UpdateIssueFragment()

        lateinit var issueEntity: IssueEntity
    }

    override val kodein by kodein()
    private val factory : UpdateIssueViewModelFactory by instance()

    private lateinit var binding: UpdateIssueFragmentBinding
    private lateinit var viewModel: UpdateIssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.update_issue_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(UpdateIssueViewModel::class.java)

        viewModel.setText(issueEntity)

        view?.findViewById<TextView>(R.id.updateIssueTitle)?.text = issueEntity.title
        view?.findViewById<TextView>(R.id.updateIssueDescription)?.text = issueEntity.description

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                UpdateIssueViewModel.UpdateIssueStatus.Started -> onStarted()
                UpdateIssueViewModel.UpdateIssueStatus.Success -> onSuccess()
                UpdateIssueViewModel.UpdateIssueStatus.Failure -> onFailure(it.error!!)
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
            updatePriorityDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.priority_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            updateAssignedUserDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.label_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            updateDepartmentDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.project_status_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            updateStatusDropdown.adapter = adapter
        }

        updateAssignedUserDropdown.onItemSelectedListener = this
        updateDepartmentDropdown.onItemSelectedListener = this
        updatePriorityDropdown.onItemSelectedListener = this
        updateStatusDropdown.onItemSelectedListener = this

        binding.viewModel = viewModel
    }

    private fun onStarted() {
    }

    private fun onSuccess() {
        Toast.makeText(context, "Success updating the issue", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_nav_update_issue_to_nav_view_issue)
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.department = updateDepartmentDropdown.selectedItem as String
        viewModel.assignedUser = updateAssignedUserDropdown.selectedItem as String
        viewModel.priority = updatePriorityDropdown.selectedItem as String
        viewModel.status = updateStatusDropdown.selectedItem as String
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        viewModel.department = updateDepartmentDropdown.selectedItem as String
        viewModel.assignedUser = updateAssignedUserDropdown.selectedItem as String
        viewModel.priority = updatePriorityDropdown.selectedItem as String
        viewModel.status = updateStatusDropdown.selectedItem as String
    }
}
