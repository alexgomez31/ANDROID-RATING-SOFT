package com.example.ratingsoft.recyclerViewUsuarios

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.R

import com.example.ratingsoft.data.users


class usersAdapter(private var jugadores: List<users>, private val onItemSelected: (Int) -> Unit) : RecyclerView.Adapter<usersViewHolder>() {

    fun updateList(jugadores: List<users>){
        this.jugadores=jugadores
        notifyDataSetChanged()
        Log.i("GABRI","LISTA: ${jugadores.size}")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): usersViewHolder {
        return usersViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_jugador, parent, false))

    }

    override fun onBindViewHolder(holder: usersViewHolder, position: Int) {

        holder.bind(jugadores[position],onItemSelected)
    }

    override fun getItemCount(): Int {
        return jugadores.size
    }


}
