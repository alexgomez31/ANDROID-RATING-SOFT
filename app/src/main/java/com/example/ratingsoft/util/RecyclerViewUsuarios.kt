package com.example.ratingsoft.util

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.data.Model.users
import com.example.ratingsoft.databinding.ItemJugadorBinding



class RecyclerViewUsuarios(view: View):RecyclerView.ViewHolder(view) {

    private val binding = ItemJugadorBinding.bind(view)

    fun bind(users: users, onItemSelected:(Int) -> Unit) {
        binding.tvPlayerName.setText(users.nombre)
        binding.root.setOnClickListener { users.id?.let { it -> onItemSelected(it) } }
    }
}