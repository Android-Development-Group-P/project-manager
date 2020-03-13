package com.example.projectmanager.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.projectmanager.R
import com.example.projectmanager.databinding.SignUpFragmentBinding
import com.example.projectmanager.util.toast
import com.example.projectmanager.data.factories.RegisterViewModelFactory
import com.example.projectmanager.ui.user_creation.UserCreationActivity
import kotlinx.android.synthetic.main.sign_up_fragment.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class RegisterFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = RegisterFragment()
    }

    override val kodein by kodein()
    private val factory : RegisterViewModelFactory by instance()

    private lateinit var binding: SignUpFragmentBinding
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.getEvent().observe(viewLifecycleOwner, Observer {
            when (it) {
                is RegisterViewModel.Event.Started -> onStarted()
                is RegisterViewModel.Event.Success -> onSuccess()
                is RegisterViewModel.Event.Failure -> onFailure(it.error!!)
            }
        })

        viewModel.getFormValidation().observe(viewLifecycleOwner, Observer {
            register_button.isEnabled = true
        })
    }

    private fun onStarted() {
        activity?.runOnUiThread {
            progress_bar.isIndeterminate = true
        }
    }

    private fun onSuccess() {
        val intent = Intent(activity, UserCreationActivity::class.java)
        startActivity(intent)
        activity?.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        activity?.finish()
    }

    private fun onFailure(error: String) {
        activity?.toast(error)

        activity?.runOnUiThread {
            progress_bar.isIndeterminate = false
        }
    }
}
