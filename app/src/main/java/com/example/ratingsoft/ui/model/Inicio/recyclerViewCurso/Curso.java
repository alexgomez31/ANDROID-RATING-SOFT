package com.example.ratingsoft.ui.model.Inicio.recyclerViewCurso;



import retrofit2.http.GET

interface ApiService {
    @GET("cursos")
    suspend fun getCursos(): List<Curso>
}
    val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.137.132:8000/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val apiService = retrofit.create(ApiService::class.java)
        lifecycleScope.launch {
        try {
        val cursos = apiService.getCursos()
        for (curso in cursos) {
        // Haz algo con cada curso (por ejemplo, imprimir en el log)
        Log.d("Curso", "ID: ${curso.id}, Nombre: ${curso.nombre}, Descripción: ${curso.descripcion}, Acciones: ${curso.acciones}")
        }
        } catch (e: Exception) {
        // Manejar errores, por ejemplo, mostrar un mensaje de error
        Log.e("Error", "Error al obtener cursos", e)
        }
        }
