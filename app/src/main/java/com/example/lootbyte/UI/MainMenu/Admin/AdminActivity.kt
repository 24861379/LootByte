package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.lootbyte.R
import com.example.lootbyte.UI.MainMenu.Admin.ReportesFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.view.View


class AdminActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_admin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, 0, systemBars.right, systemBars.bottom)
            insets
        }

        drawerLayout = findViewById(R.id.main_admin)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val header = findViewById<FrameLayout>(R.id.header_container_admin)
        header.removeAllViews()
        header.visibility = View.GONE

        cargarFragment(PerfilAdminFragment())
        bottomNav.selectedItemId = R.id.nav_perfil



        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_producto -> cargarFragment(ProductosFragment())
                R.id.nav_usuarios -> cargarFragment(UsuariosFragment())
                R.id.nav_pedidos -> {
                    findViewById<FrameLayout>(R.id.header_container_admin).removeAllViews()
                    cargarFragment(PedidosFragment())
                }
                R.id.nav_perfil -> {
                    val header = findViewById<FrameLayout>(R.id.header_container_admin)
                    header.removeAllViews()
                    header.visibility = View.GONE
                    cargarFragment(PerfilAdminFragment())
                }

            }
            true
        }
    }

    fun cargarFragment(fragment: Fragment, addToBackStack: Boolean = false) {
        val transaction = supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_admin, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    fun actualizarHeader(headerRes: Int) {
        val headerContainer = findViewById<FrameLayout>(R.id.header_container_admin)
        headerContainer.removeAllViews()
        layoutInflater.inflate(headerRes, headerContainer, true)

        val btnBack = findViewById<ImageView>(R.id.btn_back)
        btnBack?.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        findViewById<ImageView>(R.id.btn_Reportes)?.setOnClickListener {
            cargarFragment(ReportesFragment(),true)
        }
    }
}