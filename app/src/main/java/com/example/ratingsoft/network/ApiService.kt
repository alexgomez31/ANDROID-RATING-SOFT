package com.example.ratingsoft.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService{
    @POST("ruta/del/endpoint")
    fun loginUser(@Body user: String, password: String): Call<ApiResponse> // Ajusta los tipos de datos según tu API
}
val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.137.132:8000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val apiService = retrofit.create(ApiService::class.java)
