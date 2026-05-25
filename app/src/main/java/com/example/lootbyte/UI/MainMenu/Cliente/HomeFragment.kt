package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.lootbyte.Adapter.ProductoClienteAdapter
import com.example.lootbyte.Model.DetalleCarrito
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.Repository.CarritoRepository
import com.example.lootbyte.Repository.ProductoRepository
import com.example.lootbyte.databinding.FragmentHomeBinding
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ProductoClienteAdapter

    private val productoRepository = ProductoRepository()

    private val carritoRepository = CarritoRepository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecycler()

        obtenerProductos()
    }

    private fun configurarRecycler() {

        adapter = ProductoClienteAdapter(
            emptyList()
        ) { producto ->

            agregarProductoAlCarrito(producto)

        }

        binding.recyclerProductos.layoutManager =
            GridLayoutManager(requireContext(), 2)

        binding.recyclerProductos.adapter = adapter
    }

    private fun obtenerProductos() {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val productos =
                    productoRepository.obtenerProductos()

                adapter.actualizarLista(productos)

            } catch (e: Exception) {

                Toast.makeText(
                    requireContext(),
                    "Error al cargar productos",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun agregarProductoAlCarrito(
        producto: Producto
    ) {

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val detalle = DetalleCarrito(

                    id_carrito_FK = "1",

                    id_producto_color_FK =
                        producto.producto_Color
                            ?.firstOrNull()
                            ?.id_producto_color,

                    cantidad = 1
                )

                carritoRepository.agregarAlCarrito(detalle)

                Toast.makeText(
                    requireContext(),
                    "Producto agregado al carrito",
                    Toast.LENGTH_SHORT
                ).show()

            } catch (e: Exception) {

                e.printStackTrace()

                Toast.makeText(
                    requireContext(),
                    e.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}