package com.example.ratingsoft.ui.model.Inicio.recyclerViewCurso

import android.net.Uri
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.R

import com.example.ratingsoft.data.cursos
import com.example.ratingsoft.databinding.ItemParticipanteBinding


import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class CursosViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var binding = ItemParticipanteBinding.bind(view)

    fun bind(cursos: cursos) {
        binding.tvAliasParticipante.setText(cursos.descripción)
        binding.tvNombreCompleto.setText(cursos.nombre)
        binding.progresBarParticipante.visibility = View.VISIBLE

        val imagenUri = cursos.Acciones?.let { Uri.parse(it) }

        if (imagenUri != null && imagenUri.toString()!="") {
            Picasso.get().load(imagenUri).into(binding.imageViewProfileParticipante, object : Callback {
                override fun onSuccess() {
                    binding.progresBarParticipante.visibility = View.GONE
                }

                override fun onError(e: Exception?) {
                    binding.progresBarParticipante.visibility = View.GONE
                    Log.i("Gabri", "Error al cargar la imagen: $e")
                }
            })
        } else {
            // Si participante.imagen es nulo, mostrar una imagen predeterminada
            Picasso.get().load(R.drawable.perfil1).into(binding.imageViewProfileParticipante)
            binding.progresBarParticipante.visibility = View.GONE
        }
    }
}