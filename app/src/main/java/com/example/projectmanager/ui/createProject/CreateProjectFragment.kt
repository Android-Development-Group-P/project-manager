package com.example.projectmanager.ui.createProject

import android.os.Bundle
import android.util.Log
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
import com.example.projectmanager.databinding.CreateProjectFragmentBinding

class CreateProjectFragment : Fragment() {

    companion object {
        fun newInstance() = CreateProjectFragment()
    }

    private lateinit var binding: CreateProjectFragmentBinding
    private lateinit var viewModel: CreateProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.create_project_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(CreateProjectViewModel::class.java)

        viewModel.createProjectEvent.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                CreateProjectViewModel.ProjectStatus.Success -> onSuccess()
                CreateProjectViewModel.ProjectStatus.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
    }

    private fun onSuccess() {
        Toast.makeText(context, "Success creating the project", Toast.LENGTH_SHORT).show()
        view?.findNavController()?.navigate(R.id.action_nav_create_project_to_nav_project)
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

}
