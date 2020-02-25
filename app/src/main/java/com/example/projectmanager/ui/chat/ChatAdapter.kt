package com.example.projectmanager.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ChatMessageEntity
import kotlinx.android.synthetic.main.chat_fragment.view.*
import kotlinx.android.synthetic.main.chat_row.view.*
import org.w3c.dom.Text

class ChatAdapter(context: Context, list: List<ChatMessageEntity>): BaseAdapter() {

    private var messageList: List<ChatMessageEntity>
    private val mContext: Context

    init {
        mContext = context
        messageList = list
    }

    override fun getCount(): Int {
        return messageList.count()
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return messageList.get(position)
    }

    override fun getView(position: Int, convertView: View?, viewGroup: ViewGroup?): View {
        val layoutInflater = LayoutInflater.from(mContext)
        val chatRow = layoutInflater.inflate(R.layout.chat_row, viewGroup, false)

        chatRow.sender.setText(messageList[position].sender)
        chatRow.message.setText(messageList[position].message)

        return chatRow
        //val textView = TextView(mContext)


    }

}