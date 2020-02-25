package com.example.projectmanager.ui.start

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.ui.project_new.ProjectActivity
import java.io.Serializable
import java.text.SimpleDateFormat

class StartProjectsAdapter (
    val projects: List<ProjectEntity>
) : RecyclerView.Adapter<CustomViewHolder>() {

    private lateinit var context: Context

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.project_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val project = projects[position]

        holder.view.findViewById<TextView>(R.id.title_text).text = project.title
        holder.view.findViewById<TextView>(R.id.description_text).text = project.description
        holder.view.findViewById<TextView>(R.id.members_count_text).text = project.members?.size.toString()

        var date = if (project.updatedAt == null) project.createdAt else project.updatedAt
        var dateText = SimpleDateFormat("yyyy-MM-dd | HH:mm").format(date)

        holder.view.findViewById<TextView>(R.id.date_text).text = dateText

        holder.view.findViewById<Button>(R.id.button8).setOnClickListener {
            val intent = Intent(context, ProjectActivity::class.java)
            intent.putExtra(ProjectActivity.PROJECT_EXTRA_ID, project as Serializable)
            context.startActivity(intent)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }