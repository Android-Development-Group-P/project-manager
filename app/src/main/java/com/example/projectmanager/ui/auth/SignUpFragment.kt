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
import com.example.projectmanager.StartPageActivity
import com.example.projectmanager.databinding.SignUpFragmentBinding
import com.example.projectmanager.util.toast
import com.example.projectmanager.data.factories.AuthViewModelFactory
import com.example.projectmanager.view_models.AuthViewModel
import kotlinx.android.synthetic.main.sign_up_fragment.progress_bar
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class SignUpFragment : Fragment(), KodeinAware {

    companion object {
        fun newInstance() = SignUpFragment()
    }

    override val kodein by kodein()
    private val factory : AuthViewModelFactory by instance()

    private lateinit var binding: SignUpFragmentBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.sign_up_fragment, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        viewModel.event.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                AuthViewModel.AuthStatus.Started-> onStarted()
                AuthViewModel.AuthStatus.Success -> onSuccess()
                AuthViewModel.AuthStatus.Failure -> onFailure(it.error!!)
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
