package com.example.projectmanager.ui.chat

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.projectmanager.R
import com.example.projectmanager.data.factories.ChatViewModelFactory
import com.example.projectmanager.databinding.ChatFragmentBinding
import com.google.firebase.firestore.ListenerRegistration
import org.kodein.di.Factory
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class ChatFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = ChatFragment()
    }

    private lateinit var messagesListenerRegistration: ListenerRegistration

    override val kodein by kodein()
    private val factory : ChatViewModelFactory by instance()

    private lateinit var binding: ChatFragmentBinding
    private lateinit var viewModel: ChatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel.registerMessageListener("johan")

        binding =  DataBindingUtil.inflate(inflater, R.layout.chat_fragment, container, false)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(ChatViewModel::class.java)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ChatViewModel.ChatStatus.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
        viewModel.onCreateMessage()
    }

    private fun onFailure(error: String) {
        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
    }
}