package com.example.projectmanager.ui.Project

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.projectmanager.ProjectActivity

import com.example.projectmanager.R
import kotlinx.android.synthetic.main.project_fragment.*

class ProjectFragment : Fragment() {

    companion object {
        fun newInstance() = ProjectFragment()
    }

    private lateinit var viewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.project_fragment, container, false)

        val button = root.findViewById(R.id.button4) as Button
        button.setOnClickListener {
            Log.d("test", "ja")
            val intent = Intent(getActivity(), ProjectActivity::class.java)
            startActivity(intent)
        }

        return root
    }
 
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProjectViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
