package com.example.projectmanager.ui.issue

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController

import com.example.projectmanager.R

class IssueInfoFragment : Fragment() {

    companion object {
        fun newInstance() = IssueInfoFragment()
    }

    private lateinit var viewModel: IssueInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.issue_info_fragment, container, false)

        val buttonToUpdate = root.findViewById<Button>(R.id.issueInfoUpdateButton)
        val buttonToDelete = root.findViewById<Button>(R.id.issueInfoDeleteButton)

        buttonToUpdate.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_nav_view_issue_to_nav_update_issue)
        }

        buttonToDelete.setOnClickListener {view ->
            AlertDialog.Builder(context)
                .setTitle(R.string.issue_info_dialog_title)
                .setMessage(R.string.issue_info_dialog_body)
                .setPositiveButton(
                    R.string.issue_info_dialog_yes
                ) { _, _ ->
                    // Delete it.
                    view.findNavController().navigate(R.id.action_nav_view_issue_to_nav_home)
                }.setNegativeButton(
                    R.string.issue_info_dialog_no
                ) { _, _ ->
                    // Do not delete it.
                }.show()
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(IssueInfoViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
