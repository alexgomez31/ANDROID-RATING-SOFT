package com.example.ratingsoft.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    private val BASE_URL = "http://192.168.137.132:8000/api/"

    // Crear la instancia de Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Crear las instancias de las interfaces de la API
    val apiService: ApiService = retrofit.create(ApiService::class.java)
    val usersApiService: UsersApiService = retrofit.create(UsersApiService::class.java)
}
