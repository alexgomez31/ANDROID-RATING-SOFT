package com.example.ratingsoft.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.ui.main.MainActivity
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnBackNewUser.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        binding.buttomCrearCuenta.setOnClickListener {
            register()
        }

        showPassword()
        showRepeatPassword()
    }

    private fun register() {
        if (binding.editTextPassword.text.toString() == binding.editTextPaswoord2.text.toString() &&
            checkEmpty(
                binding.etNombreUsuario.text.toString(),
                binding.editTextEmail.text.toString(),
                binding.editTextPassword.text.toString(),
                binding.editTextPaswoord2.text.toString()
            )
        ) {
            // Lógica para el registro sin Firebase
            showHome()
        } else {
            showAlert()
        }
    }

    private fun showHome() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Se ha producido un error autenticando al usuario")
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun checkEmpty(
        nombreUsuario: String,
        email: String,
        password: String,
        repeatPassword: String
    ): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
    }

    private fun showPassword() {
        var isPasswordVisible = false
        val editextPassword = binding.editTextPassword
        var inputType: Int

        binding.btnshowPassword.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnshowPassword.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                binding.btnshowPassword.setImageResource(R.drawable.outline_visibility_24)
            }

            editextPassword.inputType = inputType
            editextPassword.setSelection(editextPassword.text.length)
        }
    }

    private fun showRepeatPassword() {
        var isPasswordVisible = false
        val editextPassword = binding.editTextPaswoord2
        var inputType: Int

        binding.btnshowPassword2.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (isPasswordVisible) {
                inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.btnshowPassword2.setImageResource(R.drawable.baseline_visibility_off_24)
            } else {
                inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
                binding.btnshowPassword2.setImageResource(R.drawable.outline_visibility_24)
            }

            editextPassword.inputType = inputType
            editextPassword.setSelection(editextPassword.text.length)
        }
    }
}
