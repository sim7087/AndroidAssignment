package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class Role {
    @SerializedName("key")
    var key: String? = null

    @SerializedName("rank")
    var rank: Int? = null
}