package com.example.ratingsoft.ui.Users

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.ratingsoft.ui.Users.DetailJugador.Companion.ID_PLAYER
import com.example.ratingsoft.data.Model.users
import com.example.ratingsoft.databinding.FragmentJugadoresBinding

import com.example.ratingsoft.util.usersAdapter
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class userssFragment : Fragment(), OnPlayerAddedListener {

    private var _binding: FragmentJugadoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: usersAdapter
    private var setupExecuted = false
    private val jugadores = mutableListOf<users>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJugadoresBinding.inflate(inflater,container,false)
        val view = binding.root

        adapter = usersAdapter(jugadores){ idJugador -> navigateToDetailPlayer(idJugador) }

        setup()
        searchPlayer()


        return view
    }

    fun updatePlayerList(){
        adapter.notifyDataSetChanged()

    }

    private fun searchPlayer() {

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchByName(newText.toString())
                return false
            }
        })
    }

    private fun searchByName(query: String) {
        binding.ProgresBar.isVisible=true

        val filteredJugadores = jugadores.filter { jugador ->
            jugador.nombre.contains(query, ignoreCase = true)
        }
        Log.i("GAB","NAME")
        adapter.updateList(filteredJugadores)
        binding.ProgresBar.isVisible=false
    }

    private fun setup() {
        CoroutineScope(Dispatchers.IO).launch {
            val jugadoresCollectionRef = FirebaseFirestore.getInstance().collection("jugadores")

            // Consulta los jugadores des Firestore
            jugadoresCollectionRef.get()
                .addOnSuccessListener { querySnapshot ->

                    if(!setupExecuted){
                        for (document in querySnapshot) {
                            val users = document.toObject(users::class.java)
                            jugadores.add(users)
                        }
                        setupExecuted =true
                    }
                    Log.i("GAB", jugadores.size.toString())
                    // Configura el RecyclerView y el adaptador


                    val recyclerView = binding.recyclerViewJugadores
                    recyclerView.layoutManager = LinearLayoutManager(requireContext())
                    recyclerView.adapter = adapter

                }
                .addOnFailureListener { exception ->
                    Log.e("Error", "Error al obtener los jugadores", exception)
                }
        }
    }

    private fun navigateToDetailPlayer(id:Int){
        val intent= Intent(requireContext(), DetailJugador::class.java)
        intent.putExtra(ID_PLAYER,id)
        startActivity(intent)
    }

    override fun onPlayerAdded() {
        updatePlayerList()
    }
}