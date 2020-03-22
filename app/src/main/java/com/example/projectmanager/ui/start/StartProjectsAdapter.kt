package com.example.projectmanager.ui.start

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ProjectEntity
import com.example.projectmanager.ui.project.ProjectActivity
import java.io.Serializable
import java.text.SimpleDateFormat

class StartProjectsAdapter (
    private var projects: List<ProjectEntity>
) : RecyclerView.Adapter<ProjectHolder>() {

    private lateinit var context: Context

    fun setProjects(projects: List<ProjectEntity>) {
        this.projects = projects
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return projects.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.project_row, parent, false)

        return ProjectHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectHolder, position: Int) {
        val project = projects[position]

        holder.title.text = project.title
        holder.description.text = project.description
        holder.membersCount.text = project.members?.size.toString()

        var date = if (project.updatedAt == null) project.createdAt else project.updatedAt
        var formattedDate = SimpleDateFormat("yyyy-MM-dd | HH:mm").format(date)

        holder.date.text = formattedDate

        holder.button.setOnClickListener {
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

class ProjectHolder (
    itemView: View
) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.findViewById(R.id.title_text)
    val description: TextView = itemView.findViewById(R.id.description_text)
    val membersCount: TextView = itemView.findViewById(R.id.members_count_text)
    val date: TextView = itemView.findViewById(R.id.date_text)
    val button: Button = itemView.findViewById(R.id.button8)
}



