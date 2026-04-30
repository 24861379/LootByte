package com.example.lootbyte

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lootbyte.UI.MainMenu.Cliente.CarritoFragment
import com.example.lootbyte.UI.MainMenu.Cliente.HomeFragment
import com.example.lootbyte.UI.MainMenu.Cliente.OfertasFragment
import com.example.lootbyte.UI.MainMenu.Cliente.PerfilFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout;



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

//        val toobal = findViewById<Toolbar>(R.id.toolbar)
        drawerLayout= findViewById(R.id.main)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        cargarFragment(HomeFragment())
        bottomNav.selectedItemId = R.id.nav_inicio

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_inicio -> cargarFragment(HomeFragment())
                R.id.nav_carrito-> cargarFragment(CarritoFragment())
                R.id.nav_ofertas -> cargarFragment(OfertasFragment())
                R.id.nav_perfil -> cargarFragment(PerfilFragment())
            }
            true
        }
    }

    private fun cargarFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}