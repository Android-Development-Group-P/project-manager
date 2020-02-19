package com.example.projectmanager.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.projectmanager.R
import android.widget.Button
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeViewModel()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val button = root.findViewById<Button>(R.id.button11)
        val fib = root.findViewById<FloatingActionButton>(R.id.floatingActionButton)

        button.setOnClickListener {view ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_view_issue)
        }

        fib.setOnClickListener { view ->
            view.findNavController().navigate(R.id.action_nav_home_to_nav_create_issue)
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }
}