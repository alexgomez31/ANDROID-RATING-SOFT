package com.example.ratingsoft.data.Model

import com.google.firebase.firestore.PropertyName

data class users(
    @PropertyName("id") val id: Int? = null,
    @PropertyName("nombre") val nombre: String = "",
    @PropertyName("email") val email: String = "",
    @PropertyName("puntuacion") val puntuacion: String? = ""
)
