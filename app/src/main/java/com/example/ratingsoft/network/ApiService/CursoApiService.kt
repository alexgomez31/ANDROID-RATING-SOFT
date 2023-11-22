package com.example.ratingsoft.network.ApiService

import com.example.ratingsoft.Cursos.CursoResponse
import retrofit2.Call
import retrofit2.http.GET

interface CursoApiService {

    @GET("cursos")
    fun obtenerCursos(): Call<CursoResponse>
}
