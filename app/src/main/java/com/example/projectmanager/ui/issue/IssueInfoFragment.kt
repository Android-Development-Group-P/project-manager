package com.example.projectmanager.ui.issue

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.factories.IssueInfoViewModelFactory
import com.example.projectmanager.databinding.IssueInfoFragmentBinding
import kotlinx.android.synthetic.main.issue_info_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class IssueInfoFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = IssueInfoFragment()

        lateinit var issueId : String
    }

    override val kodein by kodein()
    private val factory : IssueInfoViewModelFactory by instance()

    private lateinit var binding: IssueInfoFragmentBinding
    private lateinit var viewModel: IssueInfoViewModel
    private lateinit var issueEntity: IssueEntity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.issue_info_fragment, container, false)

        val buttonToUpdate = binding.root.findViewById<Button>(R.id.issueInfoUpdateButton)

        buttonToUpdate.setOnClickListener {view ->
            UpdateIssueFragment.issueEntity = issueEntity
            view.findNavController().navigate(R.id.action_nav_view_issue_to_nav_update_issue)
        }

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, factory).get(IssueInfoViewModel::class.java)

        viewModel.getIssue(issueId)

        view?.findViewById<Button>(R.id.issueInfoDeleteButton)?.setOnClickListener {view ->
            AlertDialog.Builder(context)
                .setTitle(R.string.issue_info_dialog_title)
                .setMessage(R.string.issue_info_dialog_body)
                .setPositiveButton(
                    R.string.issue_info_dialog_yes
                ) { _, _ ->
                    // Delete it.
                    viewModel.deleteIssue(issueId)
                }.setNegativeButton(
                    R.string.issue_info_dialog_no
                ) { _, _ ->
                    // Do not delete it.
                }.show()
        }

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                IssueInfoViewModel.IssueStatus.Started -> onStarted()
                IssueInfoViewModel.IssueStatus.Success -> onSuccess(it.issue!!)
                IssueInfoViewModel.IssueStatus.Failure -> onFailure(it.error!!)
                IssueInfoViewModel.IssueStatus.DeleteSuccess -> onDelete()
            }
        })

        binding.viewModel = viewModel
    }

    private fun onStarted() {
        /*activity?.runOnUiThread {
            progressBar.visibility = View.VISIBLE
            progressBar.isIndeterminate = true
        }*/
    }

    private fun onDelete() {
        view?.findNavController()?.navigate(R.id.action_nav_view_issue_to_nav_home)
    }

    private fun onSuccess(issue: IssueEntity) {
        issueEntity = issue
        activity?.runOnUiThread {
            progressBar.visibility = View.GONE
            progressBar.isIndeterminate = false
        }
        Toast.makeText(context, "Success creating the issue", Toast.LENGTH_SHORT).show()
        issueInfoTitleText.text = issue.title
        issueInfoDescriptionText.text = issue.description
        issueInfoStatusText.text = issue.status
    }

    private fun onFailure(error: String) {
        activity?.runOnUiThread {
            progressBar.visibility = View.GONE
            progressBar.isIndeterminate = false
        }
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}
