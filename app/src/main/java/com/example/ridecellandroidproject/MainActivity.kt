package com.example.ridecellandroidproject

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ridecellandroidproject.databinding.ActivityMainBinding
import com.example.ridecellandroidproject.fragments.ProfileFragment

import android.os.Build
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentTransaction
import com.example.ridecellandroidproject.viewModel.SignInViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.footer_layout.view.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SignInViewModel by viewModels()
    private lateinit var mMap: GoogleMap

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel.email = intent.getStringExtra("email").toString()
        viewModel.name = intent.getStringExtra("name").toString()
        viewModel.date = intent.getStringExtra("date").toString()
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            binding.fragmentContainer.id,
            ProfileFragment.newInstance(viewModel.email, viewModel.name, viewModel.getDays())
        )
        transaction.addToBackStack(null)
        transaction.commit()

        footer.map_layout.setOnClickListener {
            map.visibility = View.VISIBLE
            binding.fragmentContainer.visibility = View.GONE
            val mapFragment = supportFragmentManager
                .findFragmentById(binding.map.id) as SupportMapFragment
            mapFragment.getMapAsync(this)
        }
        footer.profile_layout.setOnClickListener {
            map.visibility = View.GONE
            binding.fragmentContainer.visibility = View.VISIBLE
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.replace(binding.fragmentContainer.id, ProfileFragment.newInstance())
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        // Add a marker in Chd and move the camera
        val chd = LatLng(30.7333, 76.7794)
        mMap.addMarker(
            MarkerOptions()
                .position(chd)
                .title("Marker in CHD")
        )
        mMap.moveCamera(CameraUpdateFactory.newLatLng(chd))
    }


    override fun onBackPressed() {
        finish()
    }
}