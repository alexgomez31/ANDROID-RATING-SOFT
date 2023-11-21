package com.example.ratingsoft.ui.Perfil

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.example.ratingsoft.ui.main.MainActivity
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.FragmentPerfilBinding



class PerfilFragment : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    private var correo: String? = null
    private var alias: String? = null
    private var nombre: String? = null
    private var telefono: String? = null
    private var localidad: String? = null
    private var posiciones: String? = null
    private var otros: String? = null
    private var foto: String? = null

    private val REQUEST_CODE_IMAGE_PICKER = 102
    private var selectedImageUri: String? = ""
    lateinit var imageViewPerfil: ImageView

    companion object {
        const val CORREO_ADMIN = "admin@gmail.com"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {



        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root


        binding.btnAddPlayer.setOnClickListener {
            startActivity(Intent(requireContext(), AddPlayerAndTournamentActivity::class.java))
        }

//        setupUi()
//        showMenuEdit()
//        saveData()
//        deleteData()

        configuration()

        return view
    }

    private fun configuration(){
        binding.buttomLogout.setOnClickListener { startActivity(Intent(requireContext(),
            Configuracion::class.java)) }
    }

    fun launchImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE_IMAGE_PICKER)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == Activity.RESULT_OK && data != null) {
//            selectedImageUri = data.data.toString()
//            showSelectedImage(selectedImageUri!!)
        }
//    }
//
////    private fun showSelectedImage(imageUri: String) {
////        .get().load(Uri.parse(imageUri))
////            .into(binding.imageViewProfile)
////    }
//
//    private fun loadEmail(correo: String) {
//        // Lógica para cargar el correo (puedes reemplazar esto con tu propia lógica)
//    }
//
//    private fun setupUi() {
//        binding.textViewCorreo.text = correo
//        binding.editTextAlias.setText(alias)
//        binding.editTextNombre.setText(nombre)
//        binding.editTextTelefono.setText(telefono)
//        binding.editTextLocalidad.setText(localidad)
//        binding.editTextOtrosDatos.setText(otros)
//        binding.editTextPosiciones.setText(posiciones)
//
//        if (correo.equals(CORREO_ADMIN)) {
//            binding.textViewTituloTorneos.visibility = View.GONE
//            binding.btnAddPlayer.visibility = View.VISIBLE
//        }
//
//        if (selectedImageUri != "") {//Para que al cambiar la imagen se actualice (solo entra si se cambia la imagen)
//            selectedImageUri = Uri.parse(selectedImageUri).toString()
//            Picasso.get().load(selectedImageUri)
//                .into(binding.imageViewProfile)
//        } else if (foto != "") { //Cargar la imagen de perfil guardada
//            selectedImageUri = Uri.parse(foto).toString()
//            Picasso.get().load(selectedImageUri)
//                .into(binding.imageViewProfile)
//        }
//
//        loadEmail(correo.toString())
//    }
//
//    private fun saveData() {
//        binding.save.setOnClickListener {
//            // Lógica para guardar datos (puedes reemplazar esto con tu propia lógica)
//            Toast.makeText(requireContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun deleteData() {
//        binding.delet.setOnClickListener {
//            showDeleteConfirmationDialog()
//        }
//    }
//
//    private fun showDeleteConfirmationDialog() {
//        val alertDialog = AlertDialog.Builder(requireContext())
//            .setTitle("Confirmar eliminación")
//            .setMessage("¿Estás seguro de que deseas borrar los datos?")
//            .setPositiveButton("Sí") { dialog, which ->
//                // Lógica para eliminar datos (puedes reemplazar esto con tu propia lógica)
//                clearEditTexts()
//            }
//            .setNegativeButton("No", null)
//            .create()
//
//        alertDialog.show()
//    }
//
//    private fun clearEditTexts() {
//        binding.editTextAlias.text.clear()
//        binding.editTextNombre.text.clear()
//        binding.editTextTelefono.text.clear()
//        binding.editTextLocalidad.text.clear()
//        binding.editTextPosiciones.text.clear()
//        binding.editTextOtrosDatos.text.clear()
//    }
//
//    private fun showMenuEdit() {
//        binding.buttomMenuEdit.setOnClickListener {
//            if (!binding.save.isVisible) {
//                binding.save.show()
//                binding.delet.show()
//                binding.buttomMenuEdit.setImageResource(R.drawable.edit_off_24)
//                enableEditTexts(true)
//            } else {
//                binding.save.hide()
//                binding.delet.hide()
//                binding.buttomMenuEdit.setImageResource(R.drawable.edit_24)
//                enableEditTexts(false)
//            }
//        }
//    }
//
//    private fun enableEditTexts(enable: Boolean) {
//        binding.btnSelectImage.isVisible = enable
//        binding.editTextAlias.isEnabled = enable
//        binding.editTextNombre.isEnabled = enable
//        binding.editTextTelefono.isEnabled = enable
//        binding.editTextLocalidad.isEnabled = enable
//        binding.editTextPosiciones.isEnabled = enable
//        binding.editTextOtrosDatos.isEnabled = enable
//        binding.editeTextEdicion.isVisible = enable
//    }
//
//    // Resto del código
//}
//
//private fun CharSequence.clear() {
//    TODO("Not yet implemented")
//}
