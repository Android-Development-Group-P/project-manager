package com.example.projectmanager.ui.Issue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.projectmanager.R

class CreateIssueFragment : Fragment() {

    companion object {
        fun newInstance() = CreateIssueFragment()
    }

    private lateinit var viewModel: CreateIssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_issue_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateIssueViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
