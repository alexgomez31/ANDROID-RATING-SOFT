package com.example.ratingsoft.ui.model.Inicio.recyclerViewCurso

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ratingsoft.R

import com.example.ratingsoft.data.cursos


class CursosAdapter (private var cursos: List<cursos>): RecyclerView.Adapter<CursosViewHolder>() {

    fun updateList(cursos: List<cursos>){
        this.cursos=cursos
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CursosViewHolder {
        return CursosViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_participante, parent,false))
    }

    override fun onBindViewHolder(holder: CursosViewHolder, position: Int) {
        holder.bind(cursos[position])
    }

    override fun getItemCount(): Int {
        return cursos.size
    }
}