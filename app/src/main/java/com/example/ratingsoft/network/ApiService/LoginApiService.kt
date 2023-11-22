package com.example.ratingsoft.network.ApiService

import com.example.ratingsoft.data.Model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface LoginApiService {
    @POST("login")
    @Headers("Content-Type: application/json")
    fun loginUser(@Body loginRequest: LoginResponse): Call<LoginResponse>
}