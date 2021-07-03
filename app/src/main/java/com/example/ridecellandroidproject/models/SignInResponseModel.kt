package com.example.ridecellandroidproject.models

import com.google.gson.annotations.SerializedName

class SignInResponseModel {
    @SerializedName("authentication_token")
    var authenticationToken: String? = null

    @SerializedName("person")
    var person: Person? = null
}