package com.example.ridecellandroidproject.utils

import android.widget.Toast
import java.util.regex.Matcher
import java.util.regex.Pattern

class ValidationUtils {

    companion object {
        private val passwordPattern: Pattern =
            Pattern.compile("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$");

        fun validateEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun validatePassword(password: String): Boolean {
            val matcher: Matcher = passwordPattern.matcher(password)
            return matcher.matches()
        }
    }
}