package com.example.projectmanager.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.example.projectmanager.R
import com.example.projectmanager.StartPageActivity
import com.example.projectmanager.databinding.SignUpFragmentBinding
import com.example.projectmanager.utilites.toast
import kotlinx.android.synthetic.main.sign_in_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.progress_bar

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    private lateinit var binding: SignUpFragmentBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.registerEvent.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                SignUpViewModel.RegisterStatus.Started-> onStarted()
                SignUpViewModel.RegisterStatus.Success -> onSuccess()
                SignUpViewModel.RegisterStatus.Failure -> onFailure(it.error!!)
            }
        })

        binding.viewModel = viewModel
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
