package com.example.ridecellandroidproject.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.ridecellandroidproject.R
import com.example.ridecellandroidproject.activities.SignInActivity
import com.example.ridecellandroidproject.utils.PreferenceHelper
import com.example.ridecellandroidproject.utils.PreferenceHelper.Companion.LAYOUT
import com.example.ridecellandroidproject.viewModel.SignInViewModel
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {
    private lateinit var preferenceHelper: PreferenceHelper
    private val viewModel: SignInViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferenceHelper = PreferenceHelper(activity?.applicationContext!!)
    }

    override fun onStart() {
        super.onStart()
        email_text.text = viewModel.email
        name_text.text = viewModel.name
        if (viewModel.totalDays > 1) {
            days_text.text = viewModel.totalDays.toString() + " Days Old"
        } else {
            days_text.text = viewModel.totalDays.toString() + " Day Old"
        }
        logout.setOnClickListener() {
            preferenceHelper.clearData()
            val intent = Intent(context, SignInActivity::class.java)
            startActivity(intent)
            activity?.finish()
            Toast.makeText(context, "User logged out", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    companion object {
        fun newInstance(): ProfileFragment {
            val profileFragment: ProfileFragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putInt(LAYOUT, R.layout.fragment_profile)
            profileFragment.arguments = bundle
            return profileFragment
        }

        fun newInstance(email: String, name: String, date: Long): ProfileFragment {
            val profileFragment = ProfileFragment()
            val bundle = Bundle()
            bundle.putInt(LAYOUT, R.layout.fragment_profile)
            bundle.putString("email", email)
            bundle.putString("name", name)
            bundle.putString("date", name)
            profileFragment.arguments = bundle
            return profileFragment
        }
    }
}