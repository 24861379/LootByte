package com.example.lootbyte.UI.MainMenu.Admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.productoAdminAdapter
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.R
import com.google.android.material.tabs.TabLayout

class ProductosFragment : Fragment() {
    private lateinit var productoAdminAdapter: productoAdminAdapter

    override fun onResume() {
        super.onResume()
        (activity as? AdminActivity)
            ?.actualizarHeader(R.layout.header_busqueda_admin)

        // Buscamos en la Actividad porque el header está en el header_container de MainActivity
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tab_categorias_admin)

        tabLayout?.let {
            if (it.tabCount == 0) {
                it.addTab(it.newTab().setText("Todo"))
                it.addTab(it.newTab().setText("Mouse"))
                it.addTab(it.newTab().setText("Teclados"))
                it.addTab(it.newTab().setText("Pantallas"))
                it.addTab(it.newTab().setText("Audífonos"))
            }
        }
    }

    private val listaProductos = listOf(
        Producto(1,"Teclado gamer RGB blanco",200000.0, R.drawable.teclado_gamer_rgb_blanco, 10,"Lleva tu experiencia de juego al siguiente nivel con este teclado gamer RGB en color blanco, diseñado para quienes buscan rendimiento, estética y comodidad en un solo dispositivo.\n\nCuenta con teclas de alta respuesta que garantizan precisión y velocidad, ideales tanto para gaming competitivo como para uso diario. Su diseño ergonómico proporciona una experiencia cómoda incluso durante largas sesiones.",detalles = listOf(
            "Iluminación RGB con múltiples efectos",
            "Respuesta rápida y precisa",
            "Diseño moderno en color blanco",
            "Construcción resistente y duradera",
            "Cable USB"
        ),
            review = listOf(
                "Excelente respuesta al escribir",
                "Muy bonito visualmente",
                "Buen rendimiento para gaming"
            )
        ),

        Producto(2,"Proyector Full HD",400000.0, R.drawable.proyector, 30,"Disfruta de una experiencia cinematográfica desde casa con este proyector Full HD de alta definición.\n\nIdeal para películas, videojuegos, presentaciones y contenido multimedia, ofrece imágenes nítidas, colores vibrantes y un rendimiento confiable en espacios interiores.",detalles = listOf(
            "Resolución Full HD 1080p",
            "Conectividad HDMI y USB",
            "Altavoz integrado",
            "Proyección hasta 120 pulgadas",
            "Diseño compacto y portátil"
        ),
            review = listOf(
                "Muy buena calidad de imagen",
                "Perfecto para cine en casa",
                "Fácil de instalar"
            )
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_productos, container, false)
        val rvProducto = view.findViewById<RecyclerView>(R.id.rvProductos)
        rvProducto.layoutManager = GridLayoutManager(requireContext(), 1)
        productoAdminAdapter = productoAdminAdapter(listaProductos) {

        }
        rvProducto.adapter = productoAdminAdapter
        //me redirecciona a otro fragment
        view.findViewById<View>(R.id.btnAgregar_producto).setOnClickListener {
            (activity as? AdminActivity)?.cargarFragment(ProductosAddFragment(),  true)
        }

        return view
    }
}


