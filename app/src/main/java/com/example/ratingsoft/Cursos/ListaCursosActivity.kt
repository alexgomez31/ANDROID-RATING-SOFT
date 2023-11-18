package com.example.ratingsoft.Cursos

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.ratingsoft.R
import com.example.ratingsoft.data.Curso
import com.example.ratingsoft.data.CursoResponse
import com.example.ratingsoft.network.CursoApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ListaCursosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_cursos)

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.80.23:8000/api/") // Reemplaza con la URL real de tu API
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(CursoApiService::class.java)

        val call = apiService.obtenerCursos()

        call.enqueue(object : Callback<CursoResponse> {
            override fun onResponse(call: Call<CursoResponse>, response: Response<CursoResponse>) {
                if (response.isSuccessful) {
                    val cursos = response.body()?.cursos
                    // Aquí puedes hacer algo con la lista de cursos
                    mostrarCursos(cursos)
                } else {
                    // Manejar errores de la respuesta
                    Log.e("API Error", "Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CursoResponse>, t: Throwable) {
                // Manejar errores de conexión o solicitud
                Log.e("API Error", "Failed to make API call", t)
            }
        })
    }

    private fun mostrarCursos(cursos: List<Curso>?) {
        // Aquí puedes implementar la lógica para mostrar la lista de cursos en tu vista
    }
}
