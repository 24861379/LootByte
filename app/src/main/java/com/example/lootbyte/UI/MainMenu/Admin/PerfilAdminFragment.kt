package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.lootbyte.R
import android.widget.ImageView
import com.google.android.material.card.MaterialCardView
import android.content.Intent
import com.example.lootbyte.MainActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class PerfilAdminFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil_admin, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnBack = view.findViewById<ImageView>(R.id.btnBack)

        btnBack.setOnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        }

        // ==================== DATOS DEL PERFIL ====================
        view.findViewById<TextView>(R.id.tvNombre)?.text = "Usuario Demo"
        view.findViewById<TextView>(R.id.tvEmail)?.text = "usuariodemo@email.com"

        // ==================== MÉTRICAS ====================
        view.findViewById<TextView>(R.id.tvTotalProductos)?.text = "248"
        view.findViewById<TextView>(R.id.tvClientesActivos)?.text = "1,234"
        view.findViewById<TextView>(R.id.tvPedidosHoy)?.text = "87"
        view.findViewById<TextView>(R.id.tvIngresos)?.text = "$45.2K"
        view.findViewById<TextView>(R.id.tvTasaConversion)?.text = "12.4%"
        view.findViewById<TextView>(R.id.tvProductosBajoStock)?.text = "15"

        val cardPedidos = view.findViewById<MaterialCardView>(R.id.cardPedidosHoy)

        cardPedidos.setOnClickListener {

            val bottomNav = requireActivity().findViewById<BottomNavigationView>(R.id.bottom_nav)
            bottomNav.selectedItemId = R.id.nav_pedidos
        }

        // ==================== BOTÓN CERRAR SESIÓN ====================
        view.findViewById<Button>(R.id.btnCerrarSesion)?.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
