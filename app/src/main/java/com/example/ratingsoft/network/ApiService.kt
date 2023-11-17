package com.example.ratingsoft.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("ruta/del/endpoint")
    fun loginUser(@Body user: User): Call<ApiResponse> // Ajusta los tipos de datos según tu API
}