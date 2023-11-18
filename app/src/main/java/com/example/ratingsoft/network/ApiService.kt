package com.example.ratingsoft.network


import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @FormUrlEncoded
    @POST("ruta/del/endpoint")
    fun loginUser(
        @Field("user") user: String,
        @Field("password") password: String
    ): Call<User> // Ajusta los tipos de datos según tu API
    @GET("ruta/del/endpoint/users")
    fun getUsers(): Call<List<User>> // Ajusta la ruta según tu API
}


// Crear la instancia de Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("http://192.168.137.132:8000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

// Crear la instancia de ApiService
val apiService = retrofit.create(ApiService::class.java)
