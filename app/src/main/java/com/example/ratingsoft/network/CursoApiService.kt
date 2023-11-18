package com.example.ratingsoft.network

import com.example.ratingsoft.data.CursoResponse
import retrofit2.Call
import retrofit2.http.GET

interface CursoApiService {

    @GET("cursos")
    fun obtenerCursos(): Call<CursoResponse>
}
