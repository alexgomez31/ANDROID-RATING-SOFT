package com.example.ratingsoft.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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

    private val BASE_URL = "http://10.185.208.93:8000/api/"

    private lateinit var apiService: LoginApiService
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRetrofit()

        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()

            // Validar campos vacíos
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Realizar la llamada a la API para autenticar
                login(email, password)
            }
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
        val loginResponse = LoginResponse(email, password)
        val call = apiService.loginUser(loginResponse)

        binding.progressBarLogin.visibility = View.VISIBLE // Mostrar ProgressBar

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                binding.progressBarLogin.visibility = View.INVISIBLE // Ocultar ProgressBar

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    Log.d(TAG, "Token: $token")

                    // TODO: Guardar el token en SharedPreferences u otro lugar seguro

                    // TODO: Navegar a la siguiente actividad

                } else {
                    Log.e(TAG, "Error en la respuesta: ${response.code()}")
                    val errorMessage = response.errorBody()?.string() ?: "Credenciales incorrectas"
                    // Manejar errores de autenticación
                    Toast.makeText(this@LoginActivity, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                binding.progressBarLogin.visibility = View.INVISIBLE // Ocultar ProgressBar

                Log.e(TAG, "Error de red: ${t.message}")
                // Manejar errores de red
                Toast.makeText(this@LoginActivity, "Error de red. Inténtalo de nuevo.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}
