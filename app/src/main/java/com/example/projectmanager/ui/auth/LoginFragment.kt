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

import com.example.projectmanager.R
import com.example.projectmanager.ui.start.StartActivity
import com.example.projectmanager.databinding.SignInFragmentBinding

import com.example.projectmanager.util.toast
import com.example.projectmanager.data.factories.LoginViewModelFactory
import kotlinx.android.synthetic.main.sign_in_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class LoginFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = LoginFragment()
    }

    override val kodein by kodein()
    private val factory : LoginViewModelFactory by instance()

    private lateinit var binding: SignInFragmentBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.sign_in_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getEvent().observe(viewLifecycleOwner, Observer {
            when (it) {
                is LoginViewModel.Event.Started -> onStarted()
                is LoginViewModel.Event.Success -> onSuccess()
                is LoginViewModel.Event.Failure -> onFailure(it.error!!)
            }
        })

        viewModel.getFormValidation().observe(viewLifecycleOwner, Observer {
            login_button.isEnabled = it
        })
    }

    private fun onStarted() {
        activity?.runOnUiThread {
            progress_bar.isIndeterminate = true
        }
    }

    private fun onSuccess() {
        val intent = Intent(activity, StartActivity::class.java)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        activity?.finish()

    }

    private fun onFailure(error: String) {
        if (error == "Invalid email or password") {
            activity?.toast(getString(R.string.login_error))
        } else {
            activity?.toast(error)
        }

        activity?.runOnUiThread {
            progress_bar.isIndeterminate = false
        }
    }
}