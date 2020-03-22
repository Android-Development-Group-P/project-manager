package com.example.projectmanager.ui.chat

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.projectmanager.R
import com.example.projectmanager.data.entities.ChatMessageEntity
import com.example.projectmanager.data.factories.ChatViewModelFactory
import com.example.projectmanager.databinding.ChatFragmentBinding
import com.example.projectmanager.ui.issue.IssuesAdapter
import com.example.projectmanager.ui.issue.IssuesFragment
import com.example.projectmanager.ui.project.ProjectActivity
import kotlinx.android.synthetic.main.chat_fragment.*
import kotlinx.android.synthetic.main.issues_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChatFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = ChatFragment()

        lateinit var adapter: ChatAdapter
    }

    override val kodein by kodein()
    private val factory : ChatViewModelFactory by instance()

    private lateinit var binding: ChatFragmentBinding
    private lateinit var viewModel: ChatViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =  DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)
        chatRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.projectId = ProjectActivity.currentProject?.id!!

        viewModel.loadLatestMessage()

        chatRecyclerView.layoutManager = LinearLayoutManager(activity)

        adapter = ChatAdapter(mutableListOf())
        chatRecyclerView.adapter = adapter

        viewModel.getLatestMessage().observe(viewLifecycleOwner, Observer {
            if (it.error != null) {

            } else {
                adapter.addItem(adapter.itemCount, it.data!!)
                chatRecyclerView.scrollToPosition(adapter.itemCount - 1)
            }
        })

        viewModel.getPreviousMessage().observe(viewLifecycleOwner, Observer {
            if (it.error != null) {
            } else {
                adapter.addItems(0, it.data!!)
                swipe_layout.isRefreshing = false
            }
        })

        swipe_layout.setOnRefreshListener {
            viewModel.loadPreviousMessages()
        }

        viewModel.getEvent().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ChatViewModel.Event.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
        viewModel.onCreateMessage(view!!)
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }

    private fun onSuccess(error: String) {
        Toast.makeText(context, "hehe", Toast.LENGTH_LONG).show()
    }


}