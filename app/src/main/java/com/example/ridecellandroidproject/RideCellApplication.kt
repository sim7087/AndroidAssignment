package com.example.ridecellandroidproject

import android.app.Application
import com.example.ridecellandroidproject.utils.PreferenceHelper
import java.lang.Appendable

class RideCellApplication : Application() {
    private lateinit var instance: RideCellApplication

    fun getInstance(): RideCellApplication? {
        return instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        PreferenceHelper(this)
    }
}