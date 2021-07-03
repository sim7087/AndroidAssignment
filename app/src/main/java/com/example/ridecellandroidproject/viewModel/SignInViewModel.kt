package com.example.ridecellandroidproject.viewModel

import android.R
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import android.widget.Toast.makeText
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import com.example.ridecellandroidproject.MainActivity
import com.example.ridecellandroidproject.activities.SignUpActivity
import com.example.ridecellandroidproject.fragments.ProfileFragment
import com.example.ridecellandroidproject.models.*
import com.example.ridecellandroidproject.network.ApiClient
import com.example.ridecellandroidproject.network.ApiInterface
import com.example.ridecellandroidproject.utils.PreferenceHelper
import kotlinx.coroutines.flow.callbackFlow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.content.DialogInterface
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.ridecellandroidproject.utils.ValidationUtils
import kotlinx.android.synthetic.main.activity_sign_in.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import java.util.concurrent.TimeUnit


class SignInViewModel : ViewModel() {

    private lateinit var apiInterface: ApiInterface
    public lateinit var progressBar: ProgressBar
    public lateinit var preferenceHelper: PreferenceHelper
    public lateinit var email: String
    public lateinit var name: String
    public lateinit var date: String
    public var totalDays: Long = 0

    fun callSignInApi(email: String, password: String, context: Context) {
        progressBar.visibility = View.VISIBLE
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val body = SignInRequestModel(email, password)
        val call: Call<SignInResponseModel>? = apiInterface.signInApi(body)
        call?.enqueue(object : Callback<SignInResponseModel?> {
            override fun onResponse(
                call: Call<SignInResponseModel?>,
                response: Response<SignInResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    preferenceHelper.write(
                        PreferenceHelper.AUTH_TOKEN,
                        response.body()?.authenticationToken
                    )
                    preferenceHelper.write(PreferenceHelper.LOG_IN_STATUS, true)
                    callGetMeApi(context)
                    makeText(context, "User Logged in successfully", Toast.LENGTH_SHORT).show()
                } else {
                    displayAlert(response.message(), context)
                }
            }

            override fun onFailure(call: Call<SignInResponseModel?>, t: Throwable) {
                progressBar.visibility = View.GONE
                displayAlert(t.message.toString(), context)
            }
        })
    }

    fun callSignUpApi(email: String, password: String, displayName: String, context: Context) {
        progressBar.visibility = View.VISIBLE
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val body = SignUpRequestModel(email, password, displayName)
        val call: Call<SignUpResponseModel>? = apiInterface.signUpApi(body)
        call?.enqueue(object : Callback<SignUpResponseModel?> {
            override fun onResponse(
                call: Call<SignUpResponseModel?>,
                response: Response<SignUpResponseModel?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    preferenceHelper.write(
                        PreferenceHelper.AUTH_TOKEN,
                        response.body()?.authenticationToken
                    )
                    callGetMeApi(context)
                    makeText(context, "User Registered", Toast.LENGTH_SHORT).show()
                } else {
                    displayAlert(response.message(), context)
                }
            }

            override fun onFailure(call: Call<SignUpResponseModel?>, t: Throwable) {
                progressBar.visibility = View.GONE
                displayAlert(t.message.toString(), context)
            }
        })
    }

    fun checkLogInStatus(context: Context) {
        if (preferenceHelper.read(PreferenceHelper.LOG_IN_STATUS, false)) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    fun callGetMeApi(context: Context) {
        progressBar.visibility = View.VISIBLE
        apiInterface = ApiClient.getClient().create(ApiInterface::class.java)
        val call: Call<GetMeResponse>? =
            apiInterface.getMe(preferenceHelper.read(PreferenceHelper.AUTH_TOKEN, ""))
        call?.enqueue(object : Callback<GetMeResponse?> {
            override fun onResponse(
                call: Call<GetMeResponse?>,
                response: Response<GetMeResponse?>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val intent = Intent(context, MainActivity::class.java)
                    intent.putExtra("email", response.body()?.email)
                    intent.putExtra("name", response.body()?.displayName)
                    intent.putExtra("date", response.body()?.createdAt)
                    context.startActivity(intent)
                } else {
                    displayAlert(response.message(), context)
                }
            }

            override fun onFailure(call: Call<GetMeResponse?>, t: Throwable) {
                progressBar.visibility = View.GONE
                displayAlert(t.message.toString(), context)
            }
        })
    }

    fun displayAlert(message: String, context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton(
                R.string.ok
            ) { dialog, which ->
                dialog.dismiss()
            }
            .setIcon(R.drawable.ic_dialog_alert)
            .setCancelable(false)
            .show()
    }

    fun checkFieldsValidation(email: String, password: String, context: Context): Boolean {
        return if (email.isEmpty()) {
            makeText(context, "Email field cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!ValidationUtils.validateEmail(email)) {
            makeText(context, "Email is Invalid", Toast.LENGTH_SHORT).show()
            false
        } else if (password.isEmpty()) {
            makeText(context, "Password field cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!ValidationUtils.validatePassword(password)) {
            makeText(context, "Password is Invalid", Toast.LENGTH_SHORT).show()
            false
        } else {
            true
        }
    }

    fun checkFieldsValidation(
        email: String,
        password: String,
        name: String,
        confirmPass: String,
        context: Context,
    ): Boolean {
        return if (email.isEmpty()) {
            makeText(context, "Email field cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!ValidationUtils.validateEmail(email)) {
            makeText(context, "Email is Invalid", Toast.LENGTH_SHORT).show()
            false
        } else if (name.isEmpty()) {
            makeText(context, "Full Name field cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (password == "") {
            makeText(context, "Password field cannot be empty", Toast.LENGTH_SHORT).show()
            false
        } else if (!ValidationUtils.validatePassword(password)) {
            makeText(context, "Password is Invalid", Toast.LENGTH_SHORT).show()
            false
        } else if (password != confirmPass) {
            makeText(context, "Confirm password differs from Password", Toast.LENGTH_SHORT).show()
            return false
        } else {
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDays(): Long {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        return try {
            val dateTwo: Date = simpleDateFormat.parse(date)
            val dateOne: Date = java.sql.Date.valueOf(LocalDate.now().toString())
            val diff: Long = dateOne.time - dateTwo.time
            totalDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)
            totalDays;
        } catch (e: ParseException) {
            e.printStackTrace()
            0
        }
    }
}
