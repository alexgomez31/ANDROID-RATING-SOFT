package com.example.ratingsoft.network.ApiService

import com.example.ratingsoft.data.Model.LoginRequest
import com.example.ratingsoft.data.Model.LoginResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}

