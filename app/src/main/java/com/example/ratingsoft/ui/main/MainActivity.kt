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
    // Declaración de variables miembro
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

    // Clase interna que actúa como adaptador para ViewPager
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

    // Constantes para identificar datos entre actividades
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

        // Configuración inicial
        binding.bottomNavigation.setBackground(null)
        loadTheme()

        // Verificar y solicitar permisos si es necesario
        if (!arePermissionsGranted()) {
            requestPermissions()
        }

        // Configurar el ViewPager y eventos de navegación
        viewPager()

        // Configurar el listener del menú de navegación inferior
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

        // Configurar el listener del botón flotante
        binding.floatinButton.setOnClickListener {
            alias?.let { it1 -> navigateToNewEvent(it1) }
        }
    }

    // Función para navegar a la pantalla de Nuevo Evento
    private fun navigateToNewEvent(usuario: String) {
        val intent = Intent(this, NuevoEvento::class.java)
        intent.putExtra(NuevoEvento.USUARIO_PUBLICADOR, usuario)
        startActivityForResult(intent, REQUEST_CODE_NUEVO_EVENTO)
    }

    // Función para mostrar el contenido principal de la actividad
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

    // Función para configurar el ViewPager y el TabLayout
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

    // Función para cargar el tema desde SharedPreferences
    private fun loadTheme() {
        val sharedPrefs = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Cargar el estado del botón y el tema
        val switchState = sharedPrefs.getBoolean("switchState", false)
        val themeMode = sharedPrefs.getInt("themeMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        // Configurar el estado del botón y el tema
        AppCompatDelegate.setDefaultNightMode(themeMode)
    }

    // Función llamada cuando se recibe un resultado de otra actividad
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_NUEVO_EVENTO && resultCode == RESULT_OK) {
            viewPager()
        }
    }

    // Función para verificar si los permisos están concedidos
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

    // Función para solicitar permisos
    fun requestPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSIONS)
    }

    // Función llamada cuando se obtiene el resultado de solicitar permisos
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

    // Función para obtener datos del usuario de la base de datos
    private fun getDataBd(correo: String) {
        // Lógica para obtener datos del usuario sin Firebase
    }

    // Función para guardar datos en SharedPreferences
    private fun saveData() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file), MODE_PRIVATE).edit()
        prefs.putString("email", "password")
        prefs.apply()
    }

    // Función para reemplazar un fragmento en el contenedor
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        // Listener para manejar la visibilidad del ViewPager y el TabLayout
        fragmentManager.addOnBackStackChangedListener {
            val isBackStackEmpty = fragmentManager.backStackEntryCount == 0
            (if (isBackStackEmpty) View.VISIBLE else View.GONE).also {
                binding.viewPager.visibility = it
            }
            binding.tabs.visibility = if (isBackStackEmpty) View.VISIBLE else View.GONE
        }
    }
}
