package com.example.ridecellandroidproject.network

import com.example.ridecellandroidproject.models.SignInRequestModel
import com.example.ridecellandroidproject.models.SignInResponseModel
import com.example.ridecellandroidproject.models.SignUpRequestModel
import com.example.ridecellandroidproject.models.SignUpResponseModel
import com.example.ridecellandroidproject.models.GetMeResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @Headers("Accept: application/json")
    @POST("/api/v2/people/authenticate")
    fun signInApi(@Body body: SignInRequestModel?): Call<SignInResponseModel>?

    @Headers("Accept: application/json")
    @POST("/api/v2/people/create")
    fun signUpApi(@Body body: SignUpRequestModel?): Call<SignUpResponseModel>?

    @Headers("Accept: application/json")
    @GET("/api/v2/people/me")
    fun getMe(@Header("Authorization") authorization: String?): Call<GetMeResponse>?

    @Headers("Accept: application/json")
    @GET("/api/v2/vehicles")
    fun getVehicle(@Header("Authorization") authorization: String?): Call<GetMeResponse?>?
}