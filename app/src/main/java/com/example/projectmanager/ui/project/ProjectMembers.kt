package com.example.projectmanager.ui.project

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.projectmanager.R
import kotlinx.android.synthetic.main.project_members_fragment.*

class ProjectMembers : Fragment() {

    companion object {
        fun newInstance() = ProjectMembers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.project_members_fragment, container, false)
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
