package com.example.ratingsoft.ui.Users

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.R
import com.example.ratingsoft.data.Model.users
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
        val usersCall = apiService.getUsers()

        usersCall.enqueue(object : Callback<List<users>> {  // Corregir el tipo de datos a List<users>
            override fun onResponse(call: Call<List<users>>, response: Response<List<users>>) {
                if (response.isSuccessful) {
                    val userList = response.body()
                    // Verifica si la lista de usuarios no es nula antes de procesarla
                    userList?.let {
                        // Haz algo con la lista de usuarios, por ejemplo, mostrarlos en una lista
                        it.forEach { user ->
                            Log.d("UserActivity", "Email: ${user.email}")  // Accede al campo email de la clase users
                        }
                    }
                } else {
                    // Manejar errores de la respuesta
                    Log.e("UserActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<users>>, t: Throwable) {
                // Manejar errores de red
                Log.e("UserActivity", "Error de red: ${t.message}")
            }
        })
    }
}
