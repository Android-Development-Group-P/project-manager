package com.example.projectmanager.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ChatMessageEntity
import kotlinx.android.synthetic.main.chat_fragment.view.*
import kotlinx.android.synthetic.main.chat_row.view.*
import org.w3c.dom.Text


class ChatAdapter (
    private var messages: MutableList<ChatMessageEntity>
) : RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return messages.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.chat_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val message = messages[position]

        holder.view.findViewById<TextView>(R.id.sender).text = message.sender
        holder.view.findViewById<TextView>(R.id.message).text = message.message
    }

    fun addItems(index: Int, items: List<ChatMessageEntity>) {
        messages.addAll(index, items)
        notifyItemRangeInserted(index, items.count())
    }

    fun addItem(index: Int, item: ChatMessageEntity) {
        messages.add(index, item)
        notifyItemInserted(index)
    }
}


class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view) { }