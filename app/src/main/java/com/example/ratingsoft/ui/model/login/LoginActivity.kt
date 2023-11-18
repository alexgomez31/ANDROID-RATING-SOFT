package com.example.ratingsoft.ui.model.login

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.data.Model.LoginResponse
import com.example.ratingsoft.databinding.ActivityLoginBinding
import com.example.ratingsoft.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val BASE_URL = "http://192.168.137.132:8000/api/"
    private lateinit var apiService: ApiService
    private lateinit var binding: ActivityLoginBinding  // Agrega esta línea

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)  // Agrega esta línea
        setContentView(binding.root)  // Agrega esta línea

        // Inicializar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia del servicio
        apiService = retrofit.create(ApiService::class.java)

        // Llamar a la función de login
        login("correo@example.com", "contraseña123")
    }

    private fun login(email: String, password: String) {
        val call = apiService.loginUser(email, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    // Manejar la respuesta exitosa, por ejemplo, guardar el token en SharedPreferences
                    val token = loginResponse?.token
                    // También puedes navegar a la siguiente actividad (MainActivity)
                    Log.d("LoginActivity", "Token: $token")
                } else {
                    // Manejar errores de autenticación
                    Log.e("LoginActivity", "Error en la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Manejar errores de red
                Log.e("LoginActivity", "Error de red: ${t.message}")
            }
        })
    }
}

private fun Any.enqueue(loginResponseCallback: Callback<LoginResponse>) {
    TODO("Not yet implemented")
}
