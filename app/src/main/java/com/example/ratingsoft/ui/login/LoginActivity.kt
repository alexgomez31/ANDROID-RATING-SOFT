package com.example.ratingsoft.ui.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.R
import com.example.ratingsoft.io.Response.LoginResponse
import com.example.ratingsoft.ui.Users.ApiService
import com.example.ratingsoft.ui.main.MainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.reflect.KProperty

class LoginActivity : AppCompatActivity() {

    private val BASE_URL = "http://10.185.208.43:8000/api/"

    // Cambia la inicialización de apiService
    private val apiService: ApiService by lazy {
        ApiService.create(BASE_URL)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val preferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        if (preferences.getString("token", "")?.contains(".") == true) {
            goToMenu()
        }

        val btnGoMain = findViewById<Button>(R.id.buttonLogin)
        btnGoMain.setOnClickListener {
            performLogin()
        }
    }

    private fun createSessionPreference(token: String) {
        val preferences = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        preferences.edit().putString("token", token).apply()
    }

    private fun performLogin() {
        val etEmail = findViewById<EditText>(R.id.et_email).text.toString()
        val etPassword = findViewById<EditText>(R.id.et_password).text.toString()

        val call = apiService.postlogin(etEmail, etPassword)
        call.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    if (loginResponse == null) {
                        Toast.makeText(applicationContext, "Se produjo un error en el servidor",
                            Toast.LENGTH_SHORT).show()
                        return
                    }
                    if (loginResponse.success) {
                        createSessionPreference(loginResponse.token)
                        goToMenu()
                    } else {
                        Toast.makeText(applicationContext, "Las credenciales son incorrectas",
                            Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(applicationContext, "Se produjo un error en el servidor",
                        Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                // Handle failure here if needed
                Toast.makeText(applicationContext, "Error de red. Verifica tu conexión e intenta de nuevo.",
                    Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToMenu() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}

private operator fun <T> Lazy<T>.getValue(loginActivity: LoginActivity, property: KProperty<T?>): ApiService {

}
