package com.example.projectmanager.ui.JoinProject

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.projectmanager.R

class JoinProjectFragment : Fragment() {

    companion object {
        fun newInstance() = JoinProjectFragment()
    }

    private lateinit var viewModel: JoinProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.join_project_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(JoinProjectViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
