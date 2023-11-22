package com.example.ratingsoft

import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ratingsoft.databinding.ActivityNuevoEventoBinding
import java.util.*

class NuevoEvento : AppCompatActivity() {
    companion object {
        const val USUARIO_PUBLICADOR = "usuario_publicador"
    }

    private lateinit var binding: ActivityNuevoEventoBinding

    private val REQUEST_CODE_IMAGE_PICKER = 102
    private var selectedImageUri: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevoEventoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.btnBackAdd.setOnClickListener { onBackPressed() }
        binding.etFecha.setOnClickListener { showDatePicker() }
        binding.etHora.setOnClickListener { showTimePicker() }
        precio()
        binding.btnImagen.setOnClickListener { selectImageEvent() }
        binding.btnDeleteImage.setOnClickListener { deleteRutaImagen() }
        binding.btnSaveTorneo.setOnClickListener { saveNewTorneo() }
    }

    private fun saveNewTorneo() {
        binding.linearLayout.clearFocus()
        if (validarCampos()) {
            saveData()
            addEventBdRealTime(binding.etNombreNewEvent.text.toString().lowercase())
        } else {
            Toast.makeText(
                this,
                "Por favor, complete todos los campos requeridos.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun validarCampos(): Boolean {
        var camposValidos = true

        val nombre = binding.etNombreNewEvent.text.toString().trim()
        val fecha = binding.etFecha.text.toString()
        val hora = binding.etHora.text.toString()
        val precio = binding.etPrecio.text.toString().trim()
        val ubicacion = binding.etUbicacion.text.toString().trim()

        if (nombre.isEmpty()) {
            camposValidos = false
            binding.tvNombreError.visibility = View.VISIBLE
            binding.tvErrorName.visibility = View.VISIBLE
        } else {
            binding.tvNombreError.visibility = View.GONE
            binding.tvErrorName.visibility = View.GONE
        }

        if (fecha.isEmpty() || hora.isEmpty() || ubicacion.isEmpty()) {
            camposValidos = false
            binding.tvFechaError.visibility = if (fecha.isEmpty()) View.VISIBLE else View.GONE
            binding.tvHoraError.visibility = if (hora.isEmpty()) View.VISIBLE else View.GONE
            binding.tvUbicacionError.visibility =
                if (ubicacion.isEmpty()) View.VISIBLE else View.GONE
            binding.tvErrorFecha.setText("* Al menos uno de los campos fecha, hora o ubicación está vacío")
            binding.tvErrorFecha.visibility = View.VISIBLE

        } else {
            binding.tvFechaError.visibility = View.GONE
            binding.tvHoraError.visibility = View.GONE
            binding.tvUbicacionError.visibility = View.GONE
            binding.tvErrorFecha.visibility = View.GONE
        }

        if (precio.isEmpty()) {
            camposValidos = false
            binding.tvPrecioError.visibility = View.VISIBLE
            binding.tvErrorPrecio.visibility = View.VISIBLE
        } else {
            binding.tvPrecioError.visibility = View.GONE
            binding.tvErrorPrecio.visibility = View.GONE
        }
        return camposValidos
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedDay, selectedMonth, selectedYear ->

                val selectedDate = "$selectedYear-${selectedMonth + 1}-$selectedDay"
                binding.etFecha.setText(selectedDate)
            },
            year,
            month,
            dayOfMonth
        )

        datePickerDialog.datePicker.minDate = Calendar.getInstance().timeInMillis
        datePickerDialog.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->

                val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                binding.etHora.setText(selectedTime)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun precio() {
        binding.etPrecio.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            @SuppressLint("SuspiciousIndentation")
            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text == "0" || text == "00") {
                    binding.etPrecio.filters = arrayOf(InputFilter.LengthFilter(6))
                    binding.etPrecio.setText("Gratis")
                } else if (text.equals("Grati")) {
                    binding.etPrecio.setText("")
                    binding.etPrecio.filters = arrayOf(InputFilter.LengthFilter(3))
                }
                binding.etPrecio.setSelection(binding.etPrecio.text.length)
            }
        })
    }

    private fun deleteRutaImagen() {
        binding.tvImagen.setText("")
        binding.btnDeleteImage.visibility = View.GONE
    }

    private fun selectImageEvent() {
        launchImagePicker()
    }

    fun launchImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
            selectedImageUri = data.data.toString()

            binding.tvImagen.setText(selectedImageUri.toString())
            binding.btnDeleteImage.visibility = View.VISIBLE
        }
    }

    private fun saveData() {
        // Aquí puedes guardar la información en tu base de datos local o en el lugar que desees.
        // Puedes usar SharedPreferences, Room Database, u otra solución según tus necesidades.
        showToast("Guardado correctamente")
        setResult(Activity.RESULT_OK)
        finish()
    }

    private fun addEventBdRealTime(nombreEvento: String) {
        // Aquí puedes agregar la lógica para guardar información en tu base de datos en tiempo real.
        // Si decides usar Firebase Realtime Database nuevamente, deberías configurar tu proyecto.
        // showToast("El evento se agregó correctamente a la bd Realtime")
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


}
