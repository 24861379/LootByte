package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.CarritoAdapter
import com.example.lootbyte.R
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.Model.ItemCarrito
import java.text.NumberFormat
import java.util.Locale

class CarritoFragment : Fragment(R.layout.fragment_carrito) {
    private lateinit var carritoAdapter: CarritoAdapter

    //    ESTO ES TEMPORAL
    private val listaItemsCarrito = mutableListOf(
        ItemCarrito(
            producto = Producto(
                id = 1,
                nombre = "Teclado gamer RGB blanco",
                precio = 200000.0,
                imagen = R.drawable.teclado_gamer_rgb_blanco,
                descripcion = "Lleva tu experiencia de juego al siguiente nivel con este teclado gamer RGB en color blanco, diseñado para quienes buscan rendimiento, estética y comodidad en un solo dispositivo.\n\nCuenta con teclas de alta respuesta que garantizan precisión y velocidad, ideales tanto para gaming competitivo como para uso diario. Su diseño ergonómico proporciona una experiencia cómoda incluso durante largas sesiones.",
                detalles = listOf(
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
            cantidad = 1
        ),

        ItemCarrito(
            producto = Producto(
                id = 2,
                nombre = "Proyector Full HD",
                precio = 400000.0,
                imagen = R.drawable.proyector,
                descripcion = "Disfruta de una experiencia cinematográfica desde casa con este proyector Full HD de alta definición.\n\nIdeal para películas, videojuegos, presentaciones y contenido multimedia, ofrece imágenes nítidas, colores vibrantes y un rendimiento confiable en espacios interiores.",
                detalles = listOf(
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
            ),
            cantidad = 2
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        val rvCarrito = view.findViewById<RecyclerView>(R.id.rvCarrito)
        rvCarrito.layoutManager = GridLayoutManager(requireContext(), 1)
        carritoAdapter = CarritoAdapter(listaItemsCarrito) {
            actualizarResumen(view)
        }
        rvCarrito.adapter = carritoAdapter
        actualizarResumen(view)

        val cbTodosProductos = view.findViewById<CheckBox>(R.id.cbTodosProductos)
        cbTodosProductos.setOnCheckedChangeListener { _, isChecked ->
            listaItemsCarrito.forEach { it.seleccionado = isChecked }
            carritoAdapter.notifyDataSetChanged()
            actualizarResumen(view)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Carrito"
    }


    private fun calcularTotal(): Double {
        return listaItemsCarrito
            .filter { it.seleccionado }
            .sumOf { it.producto.precio * it.cantidad }
    }

    private fun actualizarResumen(view: View) {
        val tvTotal = view.findViewById<TextView>(R.id.tvPrecioTotal)
        val tvCantidad = view.findViewById<TextView>(R.id.tvCantidadProductos)

        val total = calcularTotal()
        val cantidadSeleccionados = listaItemsCarrito.count { it.seleccionado }

        val formatoCOP = NumberFormat.getNumberInstance(Locale("es", "CO"))

        tvTotal.text = "$ ${formatoCOP.format(total)}"
        tvCantidad.text = "Productos ($cantidadSeleccionados)"
    }

}