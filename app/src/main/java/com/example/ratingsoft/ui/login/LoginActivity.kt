package com.example.ratingsoft.ui.login

<<<<<<< HEAD

=======
>>>>>>> a4103eb51c5eb0dab74a60d2bd1fe38eef07c5df
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

    private val BASE_URL = "http://192.168.80.23:8000/"
    private lateinit var apiService: LoginApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(LoginApiService::class.java)

        // Maneja la lógica de tu interfaz de usuario aquí, por ejemplo, al hacer clic en un botón de inicio de sesión.
        // Luego, llama a la función login con las credenciales del usuario.
        login("correo@example.com", "contraseña123")
    }

    private fun login(email: String, password: String) {
        val call = apiService.loginUser(email, password)

        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    val token = loginResponse?.token
                    Log.d("LoginActivity", "Token: $token")

                    // Aquí puedes manejar el éxito del inicio de sesión, como navegar a la siguiente actividad.
                } else {
                    Log.e("LoginActivity", "Error en la respuesta: ${response.code()}")
                    // Manejar errores de autenticación
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginActivity", "Error de red: ${t.message}")
                // Manejar errores de red
            }
        })
    }
}
