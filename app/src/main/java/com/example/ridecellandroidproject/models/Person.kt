package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class Person {
    @SerializedName("key")
    val key: String? = null

    @SerializedName("display_name")
    var displayName: String? = null

    @SerializedName("role")
    var role: Role? = null
}