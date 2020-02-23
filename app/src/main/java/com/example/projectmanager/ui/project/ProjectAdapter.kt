package com.example.projectmanager.ui.project

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.ProjectActivity
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity

class ProjectAdapter(val list: List<ProjectEntity>): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.project_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.view.findViewById<TextView>(R.id.textView5).text = list.get(position).title
        holder.view.findViewById<Button>(R.id.button8).setOnClickListener {
            /*HomeFragment.projectId = list.get(position).id!!
            val intent = Intent(holder.view.context, ProjectActivity::class.java)
            holder.view.context.startActivity(intent)*/
        }
    }

}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) {
}