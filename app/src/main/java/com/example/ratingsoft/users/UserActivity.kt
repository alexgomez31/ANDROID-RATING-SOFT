package com.example.ratingsoft.users

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.Model.User
import com.example.ratingsoft.R
import com.example.ratingsoft.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {

    private val BASE_URL = "http://tu_api_base_url/"  // Reemplaza esto con tu URL real
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        // Inicializar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia del servicio
        apiService = retrofit.create(ApiService::class.java)

        // Llamar a la función para obtener usuarios
        getUsers()
    }

    private fun getUsers() {
        val call = apiService.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    // Verifica si la lista de usuarios no es nula antes de procesarla
                    userList?.let {
                        // Haz algo con la lista de usuarios, por ejemplo, mostrarlos en una lista
                        it.forEach { user ->
                            Log.d("UserActivity", "Email: ${user.email}")
                        }
                    }
                } else {
                    // Manejar errores de la respuesta
                    Log.e("UserActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Manejar errores de red
                Log.e("UserActivity", "Error de red: ${t.message}")
            }
        })
    }
}
