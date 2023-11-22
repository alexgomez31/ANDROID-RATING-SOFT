package com.example.ratingsoft.ui.Perfil

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate

import com.example.ratingsoft.databinding.ActivityConfiguracionBinding
import com.example.ratingsoft.ui.login.LoginActivity

class Configuracion : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfiguracionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadTheme()
        setupUI()
    }

    private fun setupUI() {
        binding.imageButtonBackPerfil.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
        setupThemeSwitch()
        setupLogoutButton()
    }

    private fun setupThemeSwitch() {
        binding.temaNocturnoSwitch.setOnCheckedChangeListener { _, isChecked ->
            val themeMode = if (isChecked) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else {
                AppCompatDelegate.MODE_NIGHT_NO
            }
            AppCompatDelegate.setDefaultNightMode(themeMode)
            saveTheme(themeMode)
        }
    }

    private fun setupLogoutButton() {
        binding.tvCerrarSesion.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun showLogoutConfirmationDialog() {
        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro de que deseas cerrar sesión?")
            .setPositiveButton("Sí") { dialog, which ->
                logout()
            }
            .setNegativeButton("No", null)
            .create()

        alertDialog.show()
    }

    private fun logout() {
        // Eliminamos datos de la sesión (puedes ajustar esto según tu lógica)
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()

        // Redirigimos a la pantalla de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish()
    }

    private fun loadTheme() {
        val themeMode = AppCompatDelegate.getDefaultNightMode()

        when (themeMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> {
                binding.temaNocturnoSwitch.isChecked = false
            }
            AppCompatDelegate.MODE_NIGHT_YES -> {
                binding.temaNocturnoSwitch.isChecked = true
            }
        }
    }

    private fun saveTheme(themeMode: Int) {
        val sharedPrefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPrefs.edit()
        editor.putInt("themeMode", themeMode)
        editor.apply()
    }
}
