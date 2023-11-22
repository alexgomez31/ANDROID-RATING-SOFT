
package com.example.ratingsoft.ui.Users

import android.provider.ContactsContract.CommonDataKinds.Email
import com.example.ratingsoft.io.Response.LoginResponse
import com.example.ratingsoft.network.ApiService.UsersApiService
import com.google.gson.Gson

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @POST(value = "login")
    fun postlogin(@Query(value = "email") email:String, @Query(value = "password")password:String ):
         Call<LoginResponse>
    companion object Factory{
        private const val BASE_URL = "http://10.185.208.168:8000/api/"
        val retrofit=Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @GET("users")
    fun getUsers(): Call<List<UsersApiService>>

    // Agrega otros métodos según tus necesidades, por ejemplo, para obtener cursos, etc.
}
