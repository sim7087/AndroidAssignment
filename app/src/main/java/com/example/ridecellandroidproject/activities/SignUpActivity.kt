package com.example.ridecellandroidproject.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ridecellandroidproject.R
import com.example.ridecellandroidproject.databinding.ActivitySignUpBinding
import com.example.ridecellandroidproject.utils.PreferenceHelper
import com.example.ridecellandroidproject.viewModel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.progressBar = progress_bar
        viewModel.preferenceHelper = PreferenceHelper(applicationContext)
        sign_up.setOnClickListener {
            if (viewModel.checkFieldsValidation(
                    email_edit_text.text.trim().toString(),
                    password_edit_text.text.trim().toString(),
                    full_name_edit_text.text.trim().toString(),
                    confirm_password_edit_text.text.trim().toString(),
                    this
                )
            ) {
                viewModel.callSignUpApi(
                    binding.emailEditText.text.trim().toString(),
                    binding.passwordEditText.text.trim().toString(),
                    binding.fullNameEditText.text.trim().toString(),
                    this
                )
            }
        }
    }

    override fun onBackPressed() {
        finish()
    }
}