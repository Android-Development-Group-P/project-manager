package com.example.projectmanager.ui.CreateProject

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.projectmanager.R

class CreateProjectFragment : Fragment() {

    companion object {
        fun newInstance() = CreateProjectFragment()
    }

    private lateinit var viewModel: CreateProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_project_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(CreateProjectViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
