package com.example.ratingsoft.ui.login


import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.data.Model.LoginResponse
import com.example.ratingsoft.databinding.ActivityLoginBinding
import com.example.ratingsoft.network.ApiService.LoginApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private val BASE_URL = "http://10.185.208.90:8000/api/"

    private lateinit var apiService: LoginApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRetrofit()

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextNombre.text.toString()
            val password = binding.editTextPassword.text.toString()
            login(email, password)
        }
    }

    private fun setupRetrofit() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(LoginApiService::class.java)
    }

    private fun login(email: String, password: String) {
        val call = apiService.loginUser(email, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    Log.d(TAG, "Token: $token")

                    // Aquí puedes manejar el éxito del inicio de sesión, como navegar a la siguiente actividad.
                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                    // Manejar errores de autenticación
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(TAG, "Error de red: ${t.message}")
                // Manejar errores de red
            }
        })
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
