package com.example.projectmanager.ui.issue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.example.projectmanager.R
import com.example.projectmanager.databinding.CreateIssueFragmentBinding
import java.util.*

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

        /*
        viewModel.createProjectEvent.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                CreateProjectViewModel.ProjectStatus.Success -> onSuccess()
                CreateProjectViewModel.ProjectStatus.Failure -> onFailure(it.error!!)
            }
        })*/

        binding.viewModel = viewModel
    }

}
