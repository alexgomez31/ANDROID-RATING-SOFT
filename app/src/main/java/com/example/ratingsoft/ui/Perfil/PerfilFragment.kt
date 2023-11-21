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
import androidx.appcompat.app.AlertDialog
import com.example.ratingsoft.ui.main.MainActivity
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.FragmentPerfilBinding
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_ALIAS
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_CORREO
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_FOTO
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_LOCALIDAD
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_NOMBRE
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_OTROS
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_POSICIONES
import com.example.ratingsoft.ui.main.MainActivity.Companion.CLAVE_TELEFONO

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
            correo = it.getString(CLAVE_CORREO)
            alias = it.getString(CLAVE_ALIAS)
            nombre = it.getString(CLAVE_NOMBRE)
            telefono = it.getString(CLAVE_TELEFONO)
            localidad = it.getString(CLAVE_LOCALIDAD)
            posiciones = it.getString(CLAVE_POSICIONES)
            otros = it.getString(CLAVE_OTROS)
            foto = it.getString(CLAVE_FOTO)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.btnSelectImage.setOnClickListener {
            val mainActivity = requireActivity() as MainActivity
            launchImagePicker()
            if (mainActivity.arePermissionsGranted()) {
                Log.i("GAB", "Los permisos están concedidos")
            } else {
                mainActivity.requestPermissions()
                Log.i("GAB", "Los permisos no están concedidos")
            }
        }

        binding.btnAddPlayer.setOnClickListener {
            startActivity(Intent(requireContext(), AddPlayerAndTournamentActivity::class.java))
        }

        configuration()

        return view
    }

    private fun configuration() {
        binding.buttomLogout.setOnClickListener {
            startActivity(Intent(requireContext(), Configuracion::class.java))
        }
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
            // showSelectedImage(selectedImageUri!!)
        }
    }
}
