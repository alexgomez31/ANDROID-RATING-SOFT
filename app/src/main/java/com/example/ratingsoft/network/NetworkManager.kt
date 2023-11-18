// NetworkManager.kt

package com.example.ratingsoft.network

import com.example.ratingsoft.ui.Users.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    private val BASE_URL = "http://tu_api_base_url/"  // Reemplaza esto con tu URL real

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
