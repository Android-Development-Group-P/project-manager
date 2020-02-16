package com.example.projectmanager.ui.Issue

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.projectmanager.R

class UpdateIssueFragment : Fragment() {

    companion object {
        fun newInstance() = UpdateIssueFragment()
    }

    private lateinit var viewModel: UpdateIssueViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.update_issue_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(UpdateIssueViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
