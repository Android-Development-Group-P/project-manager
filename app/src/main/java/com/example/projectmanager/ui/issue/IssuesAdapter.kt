package com.example.projectmanager.ui.issue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity

class IssuesAdapter (
    var issues: List<IssueEntity>
) : RecyclerView.Adapter<CustomViewHolder>() {

    //private lateinit var context: Context

    override fun getItemCount(): Int {
        return issues.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.issue_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.titleTextView).text = issues.get(position).title
        holder.view.findViewById<TextView>(R.id.statusTextView).text = "Priority: ${issues.get(position).priority}"
        holder.view.findViewById<TextView>(R.id.labelTextView).text = "Description: ${issues.get(position).description}"
        holder.view.setOnClickListener {view ->
            IssueInfoFragment.issueId = issues.get(position).id!!
            IssueInfoFragment.IssueIdInRecyclerView = position
            view.findNavController().navigate(R.id.action_nav_issues_to_nav_view_issue)
        }

    }

    fun setList(issues: List<IssueEntity>) {
        this.issues = issues
        notifyDataSetChanged()
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }