package com.example.projectmanager.ui.auth

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.projectmanager.MainActivity

import com.example.projectmanager.R
import com.example.projectmanager.StartPageActivity
import com.example.projectmanager.data.managers.SessionManager
import com.example.projectmanager.databinding.SignInFragmentBinding

import com.example.projectmanager.utilites.toast
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.android.synthetic.main.sign_in_fragment.progress_bar
import kotlinx.android.synthetic.main.sign_up_fragment.*

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }

    private lateinit var binding: SignInFragmentBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        viewModel.loginEvent.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SignInViewModel.LoginStatus.Started -> onStarted()
                SignInViewModel.LoginStatus.Success -> onSuccess()
                SignInViewModel.LoginStatus.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel

        forgotten_password_button.setOnClickListener {
            val intent = Intent(activity, ResetPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onStarted() {
        activity?.toast("onStarted")
        activity?.runOnUiThread {
            progress_bar.isIndeterminate = true
        }
    }

    private fun onSuccess() {
        activity?.toast("onSuccess")
        val intent = Intent(activity, StartPageActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun onFailure(error: String) {
        activity?.toast(error)

        activity?.runOnUiThread {
            progress_bar.isIndeterminate = false
        }
    }
}
