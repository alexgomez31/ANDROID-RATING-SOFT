package com.example.ratingsoft.data.Model

import com.google.gson.annotations.SerializedName

//"user": {
//    "id": 1,
//    "name": "Juan Orozco",
//    "email": "juanjoseorozco9@gmail.com",
//    "email_verified_at": null,
//    "idpersona": 1,
//    "Avatar": "default.jpg",
//    "created_at": "2023-11-11T21:02:30.000000Z",
//    "updated_at": "2023-11-11T21:02:30.000000Z"

data class User(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("email_verified_at") val emailVerifiedAt: String?, // Puedes cambiar el tipo según tus necesidades
    @SerializedName("idpersona") val idPersona: Int,
    @SerializedName("Avatar") val avatar: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
