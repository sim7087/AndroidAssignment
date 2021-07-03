package com.example.ridecellandroidproject.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferenceHelper(context: Context) {
    companion object {
        const val LAYOUT = "layout"
        const val LOG_IN_STATUS = "isUserLoggedIn"
        const val AUTH_TOKEN = "authToken"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("SampleProject", Context.MODE_PRIVATE)

    internal fun clearData() = sharedPref.edit { clear() }

    fun read(key: String?, defValue: String?): String? {
        return sharedPref.getString(key, defValue)
    }

    fun read(key: String?, defValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defValue)
    }

    fun write(key: String?, value: String?) {
        val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun write(key: String?, value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = sharedPref.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }
}