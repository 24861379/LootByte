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
import com.example.lootbyte.R
import com.example.lootbyte.Repository.CarritoRepository
import com.example.lootbyte.Repository.ProductoRepository
import com.example.lootbyte.SupabaseClient
import com.example.lootbyte.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayout
import io.github.jan.supabase.auth.auth
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
        val tabLayout = requireActivity().findViewById<TabLayout>(R.id.tab_categorias)
        tabLayout?.let {
            if (it.tabCount == 0) {
                it.addTab(it.newTab().setText("Todo"))
                it.addTab(it.newTab().setText("Mouse"))
                it.addTab(it.newTab().setText("Teclados"))
                it.addTab(it.newTab().setText("Pantallas"))
                it.addTab(it.newTab().setText("Audífonos"))
            }

            configurarRecycler()

            obtenerProductos()
        }
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

    private fun agregarProductoAlCarrito(producto: Producto) {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                // 1. Verificar si hay usuario autenticado
                val idUsuario = SupabaseClient.client.auth.currentUserOrNull()?.id
                if (idUsuario == null) {
                    Toast.makeText(requireContext(), "Inicia sesión para agregar productos al carrito", Toast.LENGTH_SHORT).show()
                    return@launch
                }

                // 2. Obtener o crear el carrito real para este usuario
                val carrito = carritoRepository.obtenerOCrearCarrito(idUsuario)

                // 3. Preparar el detalle con el ID del carrito correcto (UUID)
                val detalle = DetalleCarrito(
                    id_carrito_FK = carrito.id_carrito,
                    id_producto_color_Fk = producto.producto_Color?.firstOrNull()?.id_producto_color,
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