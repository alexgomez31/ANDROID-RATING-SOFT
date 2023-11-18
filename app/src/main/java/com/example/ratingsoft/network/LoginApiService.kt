package com.example.ratingsoft.network

import com.example.ratingsoft.Model.LoginRequest
import com.example.ratingsoft.Model.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApiService {
    @POST("/login")
    suspend fun login(@Body loginRequest: LoginRequest): Response<LoginResponse>
}