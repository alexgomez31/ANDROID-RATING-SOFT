package com.example.ratingsoft.recyclerViewJugadores

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.data.Jugador
import com.example.ratingsoft.databinding.ItemJugadorBinding



class JugadorViewHolder(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemJugadorBinding.bind(view)

    fun bind(jugador: Jugador, onItemSelected:(Int) -> Unit) {
        binding.tvPlayerName.setText(jugador.nombre)
        binding.root.setOnClickListener { jugador.id?.let { it -> onItemSelected(it) } }
    }
}