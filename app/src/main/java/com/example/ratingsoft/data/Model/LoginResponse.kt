package com.example.ratingsoft.data.Model

data class LoginResponse(
    val token: String, // Pueden haber otros campos dependiendo de tu respuesta
    val user: User
)

data class User(
    val id: Int,
    val name: String,
    val email: String
    // Otros campos según tu modelo de usuario
)
