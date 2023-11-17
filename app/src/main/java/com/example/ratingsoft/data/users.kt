package com.example.ratingsoft.data

import com.google.firebase.firestore.PropertyName

data class users(

    @PropertyName("id") val id: Int? = null,
    @PropertyName("nombre") val nombre: String = "",
    @PropertyName("correo") val correo: String = "",
    @PropertyName("puntuacion") val puntuacion: String? = ""

)
