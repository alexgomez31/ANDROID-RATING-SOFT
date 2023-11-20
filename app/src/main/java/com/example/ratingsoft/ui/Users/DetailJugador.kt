package com.example.ratingsoft.ui.Users

import android.content.ContentResolver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.ActivityDetailJugadorBinding

import javax.security.auth.callback.Callback

class DetailJugador : AppCompatActivity() {
    companion object {
        const val ID_PLAYER = "id_player"
    }

    private lateinit var binding: ActivityDetailJugadorBinding
    private var playerId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailJugadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        playerId = intent.getIntExtra(ID_PLAYER, -1)

        getData()

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getData() {
        // Lógica para obtener datos (puedes reemplazar esto con tu propia lógica)
        // ...

        // Ejemplo de configuración de UI con datos ficticios
        val alias = "Alias"
        val nombre = "Nombre"
        val telefono = "123456789"
        val correo = "correo@example.com"
        val localidad = "Ciudad"
        val posiciones = "1ª Posición, 2ª Posición, 3ª Posición"
        val foto = "URL de la foto"
        val otros = "Otros detalles"

        setupUi(alias, nombre, telefono, correo, localidad, posiciones, foto, otros)
    }

    private fun setupUi(alias: String, nombre: String, telefono: String, correo: String, localidad: String, posiciones: String, foto: String, otros: String) {
//        if (!foto.isNullOrEmpty()) {
//            Picasso.get().load(Uri.parse(foto)).into(binding.myCircleImageView, object :
//                Callback {
//                override fun onSuccess() {
//                    binding.ProgresBarImageView.visibility = View.GONE
//                }

//                override fun onError(e: Exception?) {
//                    binding.ProgresBarImageView.visibility = View.GONE
//                    Log.i("Gabri", "Error al cargar la imagen: $e")
//                }
//            })
//        }

//        binding.tvAlias.text = alias
//        binding.tvNombre.text = nombre
//        binding.tvTelefono.text = telefono
//        binding.tvCorreo.text = correo
//        binding.tvLocalidad.text = localidad
//        binding.tvOtros.text = otros
//        binding.tvPosiciones.text = posiciones
//
//        // Lógica para cargar posiciones (puedes reemplazar esto con tu propia lógica)
//        // ...
//
//        // Ejemplo de posiciones ficticias
//        val stringBuilder = StringBuilder()
//        val separador = "◈ "
//        stringBuilder.append(separador).append("Torneo1  -  1ª Posición").appendLine().append("\n")
//        stringBuilder.append(separador).append("Torneo2  -  2ª Posición").appendLine().append("\n")
//        stringBuilder.append(separador).append("Torneo3  -  3ª Posición").appendLine().append("\n")
//
//        binding.tvTorneosDisputados.text = stringBuilder.toString()
//    }
//}
    }}