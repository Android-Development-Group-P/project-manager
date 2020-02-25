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
import kotlinx.android.synthetic.main.chat_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChatFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = ChatFragment()
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

        viewModel.getMessages().observe(viewLifecycleOwner, Observer {
            if (it.error != null) {

            } else {
                chatRecyclerView.adapter = ChatAdapter(it.data ?: listOf())
            }
        })

        viewModel.getEvent().observe(viewLifecycleOwner, Observer {
            when (it) {
                is ChatViewModel.Event.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
        viewModel.onCreateMessage()



    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }



}