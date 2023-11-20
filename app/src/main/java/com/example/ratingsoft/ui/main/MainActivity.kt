package com.example.ratingsoft.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.ratingsoft.NuevoEvento
import com.example.ratingsoft.R
import com.example.ratingsoft.databinding.ActivityMainBinding
import com.example.ratingsoft.ui.Perfil.PerfilFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import userssFragment


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var fragmentPerfil = PerfilFragment()
    private var adapter: ViewPagerFragmentAdapter? = null

    private val REQUEST_ADD_EVENT = 200
    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_NUEVO_EVENTO = 102

    private var alias: String? = null
    private var nombre: String? = null
    private var foto: String? = null
    private var telefono: String? = null
    private var localidad: String? = null
    private var posiciones: String? = null
    private var otros: String? = null

    private var args: Bundle? = null
    private val requiredPermissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)

    inner class ViewPagerFragmentAdapter(
        fragmentActivity: FragmentActivity,
        val fragments: List<Fragment>
    ) : FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int = fragments.size
        override fun createFragment(position: Int): Fragment = fragments[position]
        fun getFragment(position: Int): Fragment {
            return fragments[position]
        }
    }

    companion object {
        const val CLAVE_CORREO = "correo"
        const val CLAVE_ALIAS = "alias"
        const val CLAVE_NOMBRE = "nombre"
        const val CLAVE_TELEFONO = "telefono"
        const val CLAVE_LOCALIDAD = "localidad"
        const val CLAVE_POSICIONES = "posiciones"
        const val CLAVE_OTROS = "otros"
        const val CLAVE_FOTO = "foto"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.setBackground(null)
        loadTheme()

        if (!arePermissionsGranted()) {
            requestPermissions()
        }

        viewPager()

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    showMainActivityContent()
                }
                R.id.Buscar -> replaceFragment(userssFragment())
                R.id.perfil -> replaceFragment(fragmentPerfil)
            }
            true
        }

        binding.floatinButton.setOnClickListener {
            alias?.let { it1 -> navigateToNewEvent(it1) }
        }
    }

    private fun navigateToNewEvent(it1: String) {

    }

    private fun showMainActivityContent() {
        View.VISIBLE.also { binding.viewPager.visibility = it }
        binding.tabs.visibility = View.VISIBLE
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val fragment = fragmentManager.findFragmentById(R.id.frameContainer)
        if (fragment != null) {
            fragmentTransaction.remove(fragment)
        }
        fragmentTransaction.commit()
    }

    private fun viewPager() {
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        adapter = ViewPagerFragmentAdapter(
            this,
            listOf(
                // Agrega tus fragmentos aquí según sea necesario
            )
        )
        viewPager.adapter = adapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Eventos"
                1 -> tab.text = "Noticias"
            }
        }.attach()

        0.also { binding.viewPager.currentItem = it }
    }

    private fun loadTheme() {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Cargar el estado del botón y el tema
        val switchState = sharedPrefs.getBoolean("switchState", false)
        val themeMode = sharedPrefs.getInt("themeMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Configurar el estado del botón y el tema
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_NUEVO_EVENTO && resultCode == RESULT_OK) {
            viewPager()
        }
    }

//    private fun navigateToNewEvent(usuario: String) {
//        val intent = Intent(this, NuevoEvento::class.java)
//        intent.putExtra(NuevoEvento.USUARIO_PUBLICADOR, usuario)
//        startActivityForResult(intent, REQUEST_CODE_NUEVO_EVENTO)
//    }

//    private fun loadFoto(foto: String?) {
//        if (foto != null) {
//            val imageUri = Uri.parse(foto)
//            val picasso = Picasso.get()
//            picasso.load(imageUri).fetch(object : Callback {
//                override fun onSuccess() {
//                    // La imagen se ha precargado correctamente
//                }

//                override fun onError(e: Exception) {
//                    // Error al precargar la imagen
//                }
//            })
//        }
//    }

    fun arePermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return false
            }
        }
        return true
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSIONS)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            var allPermissionsGranted = true
            for (result in grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    allPermissionsGranted = false
                    break
                }
            }
            if (allPermissionsGranted) {
                // Los permisos fueron concedidos
            } else {
                // Los permisos no fueron concedidos
            }
        }
    }

    private fun getDataBd(correo: String) {
        // Lógica para obtener datos del usuario sin Firebase
    }

    private fun saveData() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.putString("email", "password")
        prefs.apply()
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        fragmentManager.addOnBackStackChangedListener {
            val isBackStackEmpty = fragmentManager.backStackEntryCount == 0
            (if (isBackStackEmpty) View.VISIBLE else View.GONE).also {
                binding.viewPager.visibility = it
            }
            binding.tabs.visibility = if (isBackStackEmpty) View.VISIBLE else View.GONE
        }
    }
}
