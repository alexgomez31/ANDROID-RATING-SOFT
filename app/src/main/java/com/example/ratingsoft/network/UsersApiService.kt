package com.example.ratingsoft.network

import com.example.ratingsoft.data.Model.User
import retrofit2.Call
import retrofit2.http.GET

interface UsersApiService {

    @GET("users")
    fun getUsers(): Call<List<User>> // Ajusta los tipos de datos según tu API
}
