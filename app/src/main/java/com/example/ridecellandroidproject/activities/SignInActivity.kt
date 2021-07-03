package com.example.ridecellandroidproject.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.ridecellandroidproject.databinding.ActivitySignInBinding
import com.example.ridecellandroidproject.utils.PreferenceHelper
import com.example.ridecellandroidproject.utils.ValidationUtils
import com.example.ridecellandroidproject.viewModel.SignInViewModel
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.progressBar = progress_bar
        viewModel.preferenceHelper = PreferenceHelper(applicationContext)
        viewModel.checkLogInStatus(this)
        binding.signUpText.setOnClickListener {
            val intent = Intent(view.context, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        binding.login.setOnClickListener {
            if (viewModel.checkFieldsValidation(
                    binding.emailEditText.text.trim().toString(),
                    binding.passwordEditText.text.trim().toString(),
                    this
                )
            ) {
                viewModel.callSignInApi(
                    binding.emailEditText.text.trim().toString(),
                    binding.passwordEditText.text.trim().toString(),
                    this
                )
            }
        }
    }
}
