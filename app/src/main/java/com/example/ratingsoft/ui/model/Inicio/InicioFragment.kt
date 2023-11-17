package com.example.ratingsoft.ui.model.Inicio

import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ratingsoft.data.TipoAsignatura
import com.example.ratingsoft.databinding.FragmentInicioBinding
import com.example.ratingsoft.recyclerViewAsignatura.TipoAsignaturaAdapter

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage


class InicioFragment : Fragment() {


    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private val db = FirebaseFirestore.getInstance()
    private val storageRef = FirebaseStorage.getInstance().reference
    private lateinit var tipoAsignaturaAdapter: TipoAsignaturaAdapter
    private val correo = FirebaseAuth.getInstance().currentUser?.email


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentInicioBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.progresBarEvents.visibility=View.VISIBLE
        //eventoAdapter = EventoAdapter(emptyList(),::eliminarEvento){nombreEvento -> navigateToDetailEvent(nombreEvento) }
        ensureEventoAdapterInitialized()
        binding.reyclerView.adapter = tipoAsignaturaAdapter
        binding.reyclerView.layoutManager = LinearLayoutManager(requireContext())

        if (correo != null) {
            getEventsOrderByDate(correo)
        }
        Log.i("GABRI","SE CREOOOOO BIEN: ${tipoAsignaturaAdapter.toString()}")
        return view
    }

    fun ensureEventoAdapterInitialized() {
        if (!::tipoAsignaturaAdapter.isInitialized) {
            tipoAsignaturaAdapter = TipoAsignaturaAdapter(emptyList(),::eliminarEvento){ nombreEvento -> navigateToDetailEvent(nombreEvento) }
        }
    }
    fun updateEventList(){
        ensureEventoAdapterInitialized()

        if (correo != null) {
            getEventsOrderByDate(correo)
            tipoAsignaturaAdapter.notifyDataSetChanged()
        }
    }
    fun getEventsOrderByDate(correo: String) {
        val eventsCollection = db.collection("eventos")
        val query = eventsCollection.orderBy("fecha", Query.Direction.ASCENDING)

        val currentDate = Calendar.getInstance().time

        query.get().addOnSuccessListener { document ->
            val tipoAsignaturas = document.toObjects(TipoAsignatura::class.java)
            val filteredEventos = tipoAsignaturas.filter { evento ->
                evento.fecha!! >= currentDate
            }
            tipoAsignaturaAdapter.updateList(filteredEventos)

            for (evento in filteredEventos) {
                if (evento.correo.equals(correo)) {
                    evento.mostrarBotonCancelar = true
                }
            }
            binding.progresBarEvents.visibility = View.GONE
        }
            .addOnFailureListener { exception ->
                println("Error al obtener los eventos: $exception")
            }
    }

    private fun eliminarEvento(tipoAsignatura: TipoAsignatura) {
        val alertDialog = AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas borrar el evento?")
            .setPositiveButton("Sí") { dialog, which ->
                tipoAsignatura.nombre?.let { nombreEvento ->
                    val imageRef = storageRef.child("images/${nombreEvento.lowercase()}.jpg")
                    db.collection("eventos").document(nombreEvento.lowercase()).delete()
                        .addOnSuccessListener {
                            deleteEventFromRealtimeDatabase(nombreEvento.lowercase())
                            updateEventList()
                        }
                        .addOnFailureListener { exception ->
                            println("Error al eliminar el evento: $exception")
                        }

                    imageRef.delete()
                        .addOnSuccessListener { Log.i("Gabri","Imagen borrada correctamente de Firebase Storage") }
                        .addOnFailureListener { exception -> Log.i("Gabri","Error al eliminar la imagen: $exception") }
                }
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }

    private fun deleteEventFromRealtimeDatabase(eventName: String) {
        val database = FirebaseDatabase.getInstance()
        val eventoRef = database.getReference("eventos/$eventName")

        eventoRef.removeValue()
            .addOnSuccessListener {
                Log.w("Gabri", "El evento se eliminó correctamente de la base de datos en tiempo real")
            }
            .addOnFailureListener { e ->
                Log.w("Gabri", "Error al eliminar el evento de la base de datos en tiempo real")
            }
    }

    private fun navigateToDetailEvent(nombre:String){
        val intent= Intent(requireContext(), DetailEvent::class.java)
        intent.putExtra(DetailEvent.NAME_EVENT,nombre)
        startActivity(intent)
    }


}

