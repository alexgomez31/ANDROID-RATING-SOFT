package com.example.ratingsoft.ui.Perfil

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputFilter
import android.text.InputType
import android.text.method.DigitsKeyListener
import android.util.Log
import android.view.View
import android.widget.*
import com.example.ratingsoft.data.model.User
import com.example.ratingsoft.databinding.ActivityAddPlayerAndTournamentBinding
import com.example.ratingsoft.ui.Users.OnPlayerAddedListener
import userssFragment
import java.io.Serializable
import java.util.*

class AddPlayerAndTournamentActivity : AppCompatActivity(), OnPlayerAddedListener {

    private lateinit var binding: ActivityAddPlayerAndTournamentBinding
    private val userssFragment
        get() = userssFragment()
    private val playerMatrix = mutableListOf<HashMap<String, Serializable>>()
    private val playerScores = HashMap<CheckBox, EditText>()
    private val jugadores = mutableListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPlayerAndTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPlayers()
        setupUI()
    }

    private fun setupUI() {
        binding.btnBackPerfil.setOnClickListener {
            onBackPressed()
        }
        binding.btnAddPlayer.setOnClickListener {
            saveNewPlayer()
        }
        binding.btnAddTournament.setOnClickListener {
            binding.linearLayoutPlayers.clearFocus()
            saveTournament()
        }
    }

    private fun saveTournament() {
        val tournamentName = binding.tvAddTournament.text.toString().uppercase()

        if (tournamentName.isNullOrEmpty() || playerMatrix.isEmpty()) {
            showToast(if (tournamentName.isNullOrEmpty()) "Error al guardar: Nombre de torneo necesario" else "Error al guardar: Jugadores necesarios")
            return
        }

        // Aquí puedes realizar la lógica de guardar sin Firebase
        handleSaveTournamentSuccess()

        // Limpiar los datos después de guardar
        binding.tvAddTournament.text = null
        clearPlayerInputs()
    }

    private fun handleSaveTournamentSuccess() {
        // Aquí puedes manejar la lógica después de guardar exitosamente
        Toast.makeText(this, "Añadido correctamente", Toast.LENGTH_SHORT).show()
    }

    private fun clearPlayerInputs() {
        playerScores.values.forEach { editText -> editText.text = null }
        playerScores.keys.forEach { checkBox -> checkBox.isChecked = false }
        playerMatrix.clear()
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupPlayers() {
        // Aquí puedes realizar la lógica para obtener la lista de jugadores sin Firebase
        // Puedes cargar los jugadores desde una API, base de datos local, etc.
        // Debes llenar la lista 'jugadores' con la información adecuada.
        // ...

        // Después, llamar a uiPlayers(jugadores) para mostrar la interfaz.
        uiPlayers(jugadores)
    }

    private fun uiPlayers(jugadores: MutableList<User>) {
        binding.linearLayoutPlayers.removeAllViews()

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        for (player in jugadores) {
            val linearLayout = LinearLayout(this)
            linearLayout.orientation = LinearLayout.HORIZONTAL
            linearLayout.layoutParams = layoutParams

            val nameTextView = TextView(this)
            nameTextView.text = player.nombre
            nameTextView.textSize = 16f
            nameTextView.layoutParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            linearLayout.addView(nameTextView)

            val checkBox = CheckBox(this)
            linearLayout.addView(checkBox)

            val editTextParams =
                LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
            editTextParams.setMargins(25.dpToPx(), 0, 0, 0)

            val editText = EditText(this)
            editText.hint = "Puntuación"
            editText.inputType = InputType.TYPE_CLASS_NUMBER
            editText.keyListener = DigitsKeyListener.getInstance("0123456789")
            editText.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(2))
            editText.maxLines = 1
            editText.setBackgroundColor(Color.WHITE)
            editText.layoutParams = editTextParams
            linearLayout.addView(editText)

            playerScores[checkBox] = editText
            binding.linearLayoutPlayers.addView(linearLayout)

            val playerData = HashMap<String, Serializable>()

            checkBox.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    val playerName = player.nombre
                    val playerScore = editText.text.toString()

                    if (!playerName.isNullOrEmpty() && !playerScore.isNullOrEmpty()) {
                        playerData["nombre"] = playerName
                        playerData["puntuacion"] = playerScore

                        if (!playerMatrix.contains(playerData)) {
                            playerMatrix.add(playerData)
                            Log.i("GABRI", "PLAYERMATRIX_ADD_check: $playerData")
                        }
                        Log.i("GABRI", "TAMAÑOOO check: " + playerMatrix.size.toString())
                    }
                } else {
                    playerMatrix.remove(playerData)
                    Log.i("GABRI", "TAMAÑOOO check: " + playerMatrix.size.toString())
                }
            }

            editText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) {
                    val playerName = player.nombre
                    val playerScore = editText.text.toString()

                    if (!playerName.isNullOrEmpty() && !playerScore.isNullOrEmpty()) {
                        playerData["nombre"] = playerName
                        playerData["puntuacion"] = playerScore

                        if (checkBox.isChecked) {
                            if (!playerMatrix.contains(playerData)) {
                                playerMatrix.add(playerData)
                            }
                            Log.i("GABRI", "PLAYERMATRIX_ADD: $playerData")
                        } else {
                            playerMatrix.remove(playerData)
                            Log.i("GABRI", "PLAYERMATRIX_REMOVE: $playerData")
                        }
                        Log.i("GABRI", "TAMAÑOOO: " + playerMatrix.size.toString())
                    }
                }
            }
        }
    }

    private fun saveNewPlayer() {
        val nombre = binding.editextAddNombrePlayer.text.toString().trim()

        if (nombre.isNotEmpty()) {
            val nombreCapitalizado =
                nombre.substring(0, 1).uppercase() + nombre.substring(1).lowercase()

            // Aquí puedes realizar la lógica para guardar un nuevo jugador sin Firebase
            // ...

            // Después, actualizar la lista 'jugadores' y llamar a uiPlayers(jugadores)
            // ...

            // Limpiar los datos después de guardar
            binding.editextAddNombrePlayer.setText("")
            binding.editextAddCorreoPlayer.setText("")
            binding.editextAddNombrePlayer.clearFocus()
        } else {
            Toast.makeText(this, "Por favor, ingrese un nombre válido", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPlayerAdded() {
        userssFragment?.onPlayerAdded()
    }

    // Extension function to convert dp to pixels
    private fun Int.dpToPx(): Int {
        val scale = resources.displayMetrics.density
        return (this * scale + 0.5f).toInt()
    }
}
