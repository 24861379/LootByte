package com.example.lootbyte

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lootbyte.UI.MainMenu.Cliente.CarritoFragment
import com.example.lootbyte.UI.MainMenu.Cliente.HomeFragment
import com.example.lootbyte.UI.MainMenu.Cliente.OfertasFragment
import com.example.lootbyte.UI.MainMenu.Cliente.PerfilFragment
import com.example.lootbyte.UI.Auth.LoginFragment
import com.example.lootbyte.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    var isLoggedIn = false // Variable para controlar el estado de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = binding.main

        // Configurar navegación
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> cargarFragment(HomeFragment(), R.layout.header_busqueda)
                R.id.nav_carrito -> cargarFragment(CarritoFragment(), R.layout.header_simple)
                R.id.nav_ofertas -> cargarFragment(OfertasFragment(), R.layout.header_busqueda)
                R.id.nav_perfil -> {
                    if (isLoggedIn) {
                        cargarFragment(PerfilFragment(), 0)
                    } else {
                        cargarFragment(LoginFragment(), R.layout.header_simple)
                    }
                }
            }
            true
        }

        if (savedInstanceState == null) {
            cargarFragment(HomeFragment(), R.layout.header_busqueda)
            binding.bottomNav.selectedItemId = R.id.nav_inicio
        }
    }

    fun cargarFragment(fragment: Fragment, headerRes: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        binding.headerContainer.removeAllViews()

        if (headerRes != 0) {
            binding.headerContainer.visibility = View.VISIBLE
            layoutInflater.inflate(headerRes, binding.headerContainer, true)

            val btnBack = findViewById<ImageView>(R.id.btn_back)
            btnBack?.setOnClickListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    cargarFragment(HomeFragment(), R.layout.header_busqueda)
                    binding.bottomNav.selectedItemId = R.id.nav_inicio
                }
            }
        } else {
            binding.headerContainer.visibility = View.GONE
        }
    }
}
