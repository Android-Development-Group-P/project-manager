package com.example.projectmanager.ui.issue

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.factories.UpdateIssueViewModelFactory
import com.example.projectmanager.databinding.UpdateIssueFragmentBinding
import kotlinx.android.synthetic.main.issue_info_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class UpdateIssueFragment : Fragment(), KodeinAware {

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
}
