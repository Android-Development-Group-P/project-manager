package com.example.projectmanager.ui.issue

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.example.projectmanager.R
import com.example.projectmanager.databinding.CreateIssueFragmentBinding

class CreateIssueFragment : Fragment() {

    companion object {
        fun newInstance() = CreateIssueFragment()
    }

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

        viewModel = ViewModelProvider(this).get(CreateIssueViewModel::class.java)

        viewModel.createIssueEvent.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                CreateIssueViewModel.IssueStatus.Success -> onSuccess()
                CreateIssueViewModel.IssueStatus.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
    }

    private fun onSuccess() {
        Toast.makeText(context, "Success creating the issue", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_nav_create_issue_to_nav_home)
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}
