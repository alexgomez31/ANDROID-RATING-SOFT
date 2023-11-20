package com.example.ratingsoft.util

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.R

import com.example.ratingsoft.data.model.User


class usersAdapter(private var jugadores: List<User>, private val onItemSelected: (Int) -> Unit) : RecyclerView.Adapter<RecyclerViewUsuarios>() {

    fun updateList(jugadores: List<User>){
        this.jugadores=jugadores
        notifyDataSetChanged()
        Log.i("GABRI","LISTA: ${jugadores.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewUsuarios {
        return RecyclerViewUsuarios(LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false))

    }

    override fun onBindViewHolder(holder: RecyclerViewUsuarios, position: Int) {

        holder.bind(jugadores[position],onItemSelected)
    }

    override fun getItemCount(): Int {
        return jugadores.size
    }


}
