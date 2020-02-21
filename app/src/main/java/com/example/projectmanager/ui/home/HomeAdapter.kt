package com.example.projectmanager.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.ui.issue.IssueInfoFragment

class HomeAdapter(val list: List<IssueEntity>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.home_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.textView3).text = list.get(position).title
        holder.view.setOnClickListener { view ->
            IssueInfoFragment.issueId = list.get(position).id!!
            view.findNavController().navigate(R.id.action_nav_home_to_nav_view_issue)
        }
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
}