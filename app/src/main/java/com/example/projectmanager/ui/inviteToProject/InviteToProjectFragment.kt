package com.example.projectmanager.ui.inviteToProject

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.projectmanager.R

class InviteToProjectFragment : Fragment() {

    companion object {
        fun newInstance() = InviteToProjectFragment()
    }

    private lateinit var viewModel: InviteToProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.invite_to_project_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(InviteToProjectViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
