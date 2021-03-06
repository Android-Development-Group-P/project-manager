package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.factories.UpdateIssueViewModelFactory
import com.example.projectmanager.databinding.UpdateIssueFragmentBinding
import com.example.projectmanager.ui.project.ProjectActivity
import com.example.projectmanager.util.toast
import kotlinx.android.synthetic.main.create_issue_fragment.*
import kotlinx.android.synthetic.main.update_issue_fragment.*
import kotlinx.android.synthetic.main.update_issue_fragment.progressBar
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

        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }

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
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            updatePriorityDropdown.adapter = adapter
        }

        val list = mutableListOf<String>()
        list.add("Anyone")
        for (member in ProjectActivity.members) {
            list.add(member.email ?:"Anyone")
        }
        val adapterUsers = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, list)
        adapterUsers.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        updateAssignedUserDropdown.adapter = adapterUsers

        ArrayAdapter.createFromResource(
            context!!,
            R.array.label_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            updateDepartmentDropdown.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            context!!,
            R.array.project_status_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            updateStatusDropdown.adapter = adapter
        }

        updateAssignedUserDropdown.onItemSelectedListener = this
        updateDepartmentDropdown.onItemSelectedListener = this
        updatePriorityDropdown.onItemSelectedListener = this
        updateStatusDropdown.onItemSelectedListener = this

        binding.viewModel = viewModel
    }

    private fun onStarted() {
        activity?.runOnUiThread {
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
        }
    }

    private fun onSuccess() {
        activity?.toast(getString(R.string.issue_update_message))
        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }
        activity!!.supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment_project, IssueInfoFragment()
        ).commit()
    }

    private fun onFailure(error: String) {
        if (error == "You must write something in both fields") {
            activity?.toast(getString(R.string.issue_update_input_error))
        } else {
            activity?.toast(error)
        }
        activity?.runOnUiThread {
            progressBar.visibility = View.INVISIBLE
            progressBar.isIndeterminate = true
        }
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
