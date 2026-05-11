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
import com.example.lootbyte.databinding.ActivityMainBinding
import com.example.lootbyte.UI.Auth.LoginFragment

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var binding: ActivityMainBinding
    var isLoggedIn = false // Variable para controlar el estado de la sesión

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // 1. Inicializar binding y establecer el contenido una sola vez
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = binding.main

        // 2. Configurar el listener de navegación del BottomNavigationView
        binding.bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> cargarFragment(HomeFragment(), R.layout.header_busqueda)
                R.id.nav_carrito -> cargarFragment(CarritoFragment(), R.layout.header_simple)
                R.id.nav_ofertas -> cargarFragment(OfertasFragment(), R.layout.header_busqueda)
                R.id.nav_perfil -> {
                    // Si ya inició sesión, mostramos PerfilFragment sin header (ya tiene el suyo), de lo contrario LoginFragment
                    if (isLoggedIn) {
                        cargarFragment(com.example.lootbyte.UI.MainMenu.Cliente.PerfilFragment(), 0)
                    } else {
                        cargarFragment(com.example.lootbyte.UI.Auth.LoginFragment(), R.layout.header_simple)
                    }
                }
            }
            true
        }

        // 3. Cargar HomeFragment por defecto al iniciar la aplicación si es la primera vez
        if (savedInstanceState == null) {
            cargarFragment(HomeFragment(), R.layout.header_busqueda)
            binding.bottomNav.selectedItemId = R.id.nav_inicio
        }
    }

    private fun cargarFragment(fragment: Fragment, headerRes: Int) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()

        // Actualizar el contenedor del header usando el binding
        binding.headerContainer.removeAllViews()

        if (headerRes != 0) {
            binding.headerContainer.visibility = View.VISIBLE
            layoutInflater.inflate(headerRes, binding.headerContainer, true)

            // Configurar el botón de retroceso si existe en el header recién inflado
            val btnBack = findViewById<ImageView>(R.id.btn_back)
            btnBack?.setOnClickListener {
                if (supportFragmentManager.backStackEntryCount > 0) {
                    supportFragmentManager.popBackStack()
                } else {
                    // Volver al inicio si no hay nada en el stack
                    cargarFragment(HomeFragment(), R.layout.header_busqueda)
                    binding.bottomNav.selectedItemId = R.id.nav_inicio
                }
            }
        } else {
            binding.headerContainer.visibility = View.GONE
        }
    }
}
//        PRUEBAS
//        val btnAdminTest = findViewById<Button>(R.id.btnAdminTest)
//
//        btnAdminTest.setOnClickListener {
//            val intent = Intent(this, AdminActivity::class.java)
//            startActivity(intent)
//        }