package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class GetMeResponse {
    @SerializedName("created_at")
    val createdAt: String? = null

    @SerializedName("display_name")
    var displayName: String? = null

    @SerializedName("role")
    var role: Role? = null

    @SerializedName("key")
    var key: String? = null

    @SerializedName("updated_at")
    var updatedAt: String? = null

    @SerializedName("email")
    var email: String? = null
}