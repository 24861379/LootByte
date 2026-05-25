package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.CarritoAdapter
import com.example.lootbyte.Model.ItemCarrito
import com.example.lootbyte.R
import com.example.lootbyte.Repository.CarritoRepository
import com.example.lootbyte.SupabaseClient
import com.example.lootbyte.UI.SeccionPagos.DatosDeEnvioFragment
import io.github.jan.supabase.auth.auth
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

class CarritoFragment : Fragment(R.layout.fragment_carrito) {
    private lateinit var carritoAdapter: CarritoAdapter
    private val carritoRepository = CarritoRepository()
    private var listaItemsCarrito = mutableListOf<ItemCarrito>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        val rvCarrito = view.findViewById<RecyclerView>(R.id.rvCarrito)
        rvCarrito.layoutManager = GridLayoutManager(requireContext(), 1)

        cargarCarrito(view, rvCarrito)
        configurarCheckbox(view)
        configurarBotonContinuar(view)

        return view
    }

    private fun cargarCarrito(view: View, rvCarrito: RecyclerView) {
        lifecycleScope.launch {
            try {
                // Obtiene el ID del usuario de la sesión actual de Supabase
                val idUsuario = SupabaseClient.client.auth.currentUserOrNull()?.id

                if (idUsuario == null) {
                    Toast.makeText(requireContext(), "Inicia sesión para ver tu carrito", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                val carrito = carritoRepository.obtenerOCrearCarrito(idUsuario)
                val detalleCarrito = carritoRepository.obtenerDetalleCarrito(carrito.id_carrito!!)
                val itemsCarrito = detalleCarrito.mapNotNull { detalle ->
                    val productoColor = detalle.producto_Color
                    val producto = productoColor?.producto

                    if (producto != null && productoColor != null) {
                        ItemCarrito(
                            id_detalle_carrito = detalle.id_detalle_carrito,
                            producto = producto,
                            productoColor = productoColor,
                            cantidad = detalle.cantidad,
                            seleccionado = true
                        )
                    } else {
                        null
                    }
                }

                listaItemsCarrito.clear()
                listaItemsCarrito.addAll(itemsCarrito)

                carritoAdapter = CarritoAdapter(
                    listaItemsCarrito,
                    onCambio = {
                        actualizarResumen(view)
                    },
                    onCantidadCambiada = { item ->
                        lifecycleScope.launch {
                            try {
                                carritoRepository.actualizarCantidad(
                                    item.id_detalle_carrito!!,
                                    item.cantidad
                                )
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    },
                    onEliminar = { item, position ->
                        lifecycleScope.launch {
                            try {
                                carritoRepository.eliminarDetalleCarrito(item.id_detalle_carrito!!)
                                listaItemsCarrito.removeAt(position)
                                carritoAdapter.notifyItemRemoved(position)
                                actualizarResumen(view)
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                )

                rvCarrito.adapter = carritoAdapter
                actualizarResumen(view)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun configurarCheckbox(view: View) {
        val cbTodosProductos = view.findViewById<CheckBox>(R.id.cbTodosProductos)
        cbTodosProductos.setOnCheckedChangeListener { _, isChecked ->
            listaItemsCarrito.forEach {
                it.seleccionado = isChecked
            }
            carritoAdapter.notifyDataSetChanged()
            actualizarResumen(view)
        }
    }

    private fun configurarBotonContinuar(view: View) {
        view.findViewById<View>(R.id.btn_ContinuarCompra).setOnClickListener {
            val total = calcularTotal()
            val fragment = DatosDeEnvioFragment()
            val bundle = Bundle()
            bundle.putDouble("total_compra", total)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val titulo = requireActivity().findViewById<TextView>(R.id.tvTitulo)
        titulo?.text = "Carrito"
    }

    private fun calcularTotal(): Double {
        return listaItemsCarrito
            .filter { it.seleccionado }
            .sumOf { it.productoColor.precio * it.cantidad }
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
