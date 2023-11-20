package com.example.ratingsoft.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.ratingsoft.databinding.ActivityResetPasswordBinding

class ResetPassword : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    companion object {
        const val CLAVE_CORREO_RESET = "correo_reset"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent
        val correo = intent.getStringExtra(CLAVE_CORREO_RESET)
        binding.editTextResetCorreo.setText(correo)

        binding.btnResetPassword.setOnClickListener { resetPassword() }
        binding.btnBackResetPassword.setOnClickListener { onBackPressedDispatcher.onBackPressed() }
    }

    private fun resetPassword() {
        val correo = binding.editTextResetCorreo.text.toString().trim()
        binding.progresBarResetPassword.visibility = View.VISIBLE // Mostrar ProgressBar

        if (correo.isNotEmpty()) {
            // Lógica para enviar el correo de restablecimiento de contraseña (sin Firebase)
            Toast.makeText(
                this,
                "Revise su correo electrónico para restablecer su contraseña",
                Toast.LENGTH_SHORT
            ).show()
            showSuccessful(correo)
        } else {
            Toast.makeText(
                this,
                "Inserte el correo electrónico para restablecer la contraseña",
                Toast.LENGTH_SHORT
            ).show()
            binding.progresBarResetPassword.visibility = View.INVISIBLE // Ocultar ProgressBar si no hay correo electrónico
        }
    }

    private fun showSuccessful(correo: String) {
        binding.tvSuccessful.text =
            "Consulte la dirección de correo electrónico ${correo.uppercase()} para restablecer su contraseña.\n Si no le aparece, revise su carpeta de spam."
        binding.tvSuccessful.visibility = View.VISIBLE
        binding.tvInstrucciones.visibility = View.GONE
        binding.editTextResetCorreo.visibility = View.GONE
        binding.editTextResetCorreo2.visibility = View.GONE
        binding.btnResetPassword.text = "Reenviar email"
        binding.progresBarResetPassword.visibility = View.INVISIBLE
    }
}
