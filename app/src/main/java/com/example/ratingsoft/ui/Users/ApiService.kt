
package com.example.ratingsoft.ui.Users

import com.example.ratingsoft.data.Model.LoginResponse
import com.example.ratingsoft.network.ApiService.UsersApiService

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("login")
    fun loginUser(@Body loginRequest: String, password: String): Call<LoginResponse>

    @GET("users")
    fun getUsers(): Call<List<UsersApiService>>

    // Agrega otros métodos según tus necesidades, por ejemplo, para obtener cursos, etc.
}
