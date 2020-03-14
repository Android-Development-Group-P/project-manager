package com.example.projectmanager.ui.issue

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity


class IssuesAdapter (
    var issues: List<IssueEntity>
) : RecyclerView.Adapter<CustomViewHolder>() {

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
        holder.view.findViewById<TextView>(R.id.statusTextView).text = "${issues.get(position).priority}"
        holder.view.findViewById<TextView>(R.id.labelTextView).text = "${issues.get(position).description}"
        holder.view.setOnClickListener {view ->
            IssueInfoFragment.issueId = issues.get(position).id!!
            IssueInfoFragment.IssueIdInRecyclerView = position
            (view.context as AppCompatActivity).supportFragmentManager.beginTransaction().replace(
                R.id.nav_host_fragment_project, IssueInfoFragment()
            ).commit()
        }

    }

    fun setList(issues: List<IssueEntity>) {
        this.issues = issues
        notifyDataSetChanged()
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }