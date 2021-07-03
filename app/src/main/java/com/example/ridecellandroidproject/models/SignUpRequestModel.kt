package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class SignUpRequestModel(
    @field:SerializedName("email") var isEmail: String, @field:SerializedName(
        "password"
    ) var isPassword: String, @field:SerializedName("display_name") var displayName: String
)