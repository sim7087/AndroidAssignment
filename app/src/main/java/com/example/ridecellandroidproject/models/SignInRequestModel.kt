package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class SignInRequestModel(
    @field:SerializedName("email") var isEmail: String, @field:SerializedName(
        "password"
    ) var isPassword: String
)