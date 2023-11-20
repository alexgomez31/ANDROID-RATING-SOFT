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
import com.example.ratingsoft.data.model.User
import com.example.ratingsoft.databinding.FragmentJugadoresBinding
import com.example.ratingsoft.ui.Users.DetailJugador
import com.example.ratingsoft.ui.Users.OnPlayerAddedListener
import com.example.ratingsoft.util.usersAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class userssFragment : Fragment(), OnPlayerAddedListener {

    private var _binding: FragmentJugadoresBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: usersAdapter
    private var setupExecuted = false
    private val jugadores = mutableListOf<User>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentJugadoresBinding.inflate(inflater, container, false)
        val view = binding.root

        adapter = usersAdapter(jugadores) { idJugador -> navigateToDetailPlayer(idJugador) }

        setup()
        searchPlayer()

        return view
    }

    fun updatePlayerList() {
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
        binding.ProgresBar.isVisible = true

        val filteredJugadores = jugadores.filter { jugador ->
            jugador.nombre.contains(query, ignoreCase = true)
        }

        Log.i("GAB", "NAME")
        adapter.updateList(filteredJugadores)
        binding.ProgresBar.isVisible = false
    }

    private fun setup() {
        // Lógica para obtener datos (puedes reemplazar esto con tu propia lógica)
        // ...

        // Ejemplo de configuración de datos ficticios
        val jugadoresFicticios = listOf(
            User(id = 1, nombre = "Jugador1"),
            User(id = 2, nombre = "Jugador2"),
            User(id = 3, nombre = "Jugador3")
        )

        jugadores.addAll(jugadoresFicticios)

        if (!setupExecuted) {
            val recyclerView = binding.recyclerViewJugadores
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            setupExecuted = true
        }

        Log.i("GAB", jugadores.size.toString())
    }

    private fun navigateToDetailPlayer(id: Int) {
        val intent = Intent(requireContext(), DetailJugador::class.java)
        intent.putExtra(ID_PLAYER, id)
        startActivity(intent)
    }

    override fun onPlayerAdded() {
        TODO("Not yet implemented")
    }

//    override fun onPlayerAdded() {
//        updatePlayerList()
//    }
}
