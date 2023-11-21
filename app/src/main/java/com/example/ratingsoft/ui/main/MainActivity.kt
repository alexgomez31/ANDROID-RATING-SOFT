package com.example.ratingsoft.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
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

    private val REQUEST_CODE_PERMISSIONS = 101
    private val REQUEST_CODE_NUEVO_EVENTO = 102

    private var alias: String? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigation.background = null
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

        }



    private fun showMainActivityContent() {
        binding.viewPager.visibility = View.VISIBLE
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
                userssFragment(),

            )
        )
        viewPager.adapter = adapter

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "Eventos"
                1 -> tab.text = "Noticias"
            }
        }.attach()

        viewPager.currentItem = 0
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

    private fun arePermissionsGranted(): Boolean {
        for (permission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(this, requiredPermissions, REQUEST_CODE_PERMISSIONS)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.frameContainer, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        fragmentManager.addOnBackStackChangedListener {
            val isBackStackEmpty = fragmentManager.backStackEntryCount == 0
            binding.viewPager.visibility = if (isBackStackEmpty) View.VISIBLE else View.GONE
            binding.tabs.visibility = if (isBackStackEmpty) View.VISIBLE else View.GONE
        }
    }
}
