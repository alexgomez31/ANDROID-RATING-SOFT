package com.example.ratingsoft.network.ApiService

import com.example.ratingsoft.data.Model.users
import retrofit2.Call
import retrofit2.http.GET

interface UsersApiService {

    @GET("users")
    fun getUsers(): Call<List<users>> // Ajusta los tipos de datos según tu API
}
