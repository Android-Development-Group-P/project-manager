package com.example.projectmanager.ui.user_creation

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.projectmanager.R
import com.example.projectmanager.StartPageActivity
import com.example.projectmanager.data.factories.UserCreationViewModelFactory
import com.example.projectmanager.databinding.ActivityUserCreationBinding
import com.example.projectmanager.view_models.UserCreationViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class UserCreationActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory : UserCreationViewModelFactory by instance()

    private lateinit var viewModel: UserCreationViewModel
    private lateinit var binding: ActivityUserCreationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_creation)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_creation);

        // Hide top bar
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this, factory).get(UserCreationViewModel::class.java)

        viewModel.event?.observe(this, Observer {
            when (it.status) {
                UserCreationViewModel.UserCreationStatus.Success -> onSuccess()

                UserCreationViewModel.UserCreationStatus.Skipped -> onSkipped()

                UserCreationViewModel.UserCreationStatus.Failure -> {
                    Log.d("Hejhej", "Failure")
                }
            }
        })

        binding.viewModel = viewModel

        pickImageFromGallery()
    }

    private fun onSuccess() {
        val intent = Intent(this, StartPageActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun onSkipped() = onSuccess()

    private fun onFailure() {

    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 1000) // GIVE AN INTEGER VALUE FOR IMAGE_PICK_CODE LIKE 1000
    }
}