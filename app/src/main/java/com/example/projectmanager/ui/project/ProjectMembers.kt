package com.example.projectmanager.ui.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.projectmanager.R
import com.example.projectmanager.data.factories.IssuesViewModelFactory
import com.example.projectmanager.ui.project.ProjectActivity
import kotlinx.android.synthetic.main.fragment_issues.*
import kotlinx.android.synthetic.main.fragment_project_members.*
import kotlinx.android.synthetic.main.issues_layout.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ProjectMembers : Fragment() {

    companion object {
        fun newInstance() = ProjectMembers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_project_members, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val list = mutableListOf<String>()
        for (member in ProjectActivity.members) {
            list.add(member.displayName ?: (member.email ?: "ProjectManager Bot"))
        }
        val adapterUsers = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, list)
        listView.adapter = adapterUsers
    }
}
