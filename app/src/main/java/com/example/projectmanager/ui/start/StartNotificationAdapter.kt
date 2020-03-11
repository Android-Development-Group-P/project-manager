package com.example.projectmanager.ui.start

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.IssueEntity
import com.example.projectmanager.data.entities.NotificationEntity
import com.example.projectmanager.ui.chat.CustomViewHolder

class StartNotificationAdapter (
    private var notifications: List<NotificationEntity>
) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.row_start_notification, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val notification = notifications[position]

        holder.view.findViewById<TextView>(R.id.notificationIssue).text = notification.issue
        holder.view.findViewById<TextView>(R.id.notificationProject).text = notification.project

    }

    fun updateNotificationList(notificationUpdate: List<NotificationEntity>) {
        notifications = notificationUpdate
        notifyDataSetChanged()
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }
