package com.example.lootbyte.UI.MainMenu.Admin

import android.app.AlertDialog
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Adapter.productoAdminAdapter
import com.example.lootbyte.R
import com.example.lootbyte.Repository.ProductoRepository
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.launch

class ProductosFragment : Fragment() {
    private lateinit var productoAdminAdapter: productoAdminAdapter
    private val productoRepository = ProductoRepository()

    override fun onResume() {
        super.onResume()
        (activity as? AdminActivity)
            ?.actualizarHeader(R.layout.header_busqueda_admin)

        view?.post{
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


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun cargarProductos() {
        lifecycleScope.launch {
            try {
                val productos = productoRepository.obtenerProductos()
                productoAdminAdapter.actualizarProductos(productos)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_productos, container, false)
        val rvProducto = view.findViewById<RecyclerView>(R.id.rvProductos)
        rvProducto.layoutManager = GridLayoutManager(requireContext(), 1)
        productoAdminAdapter = productoAdminAdapter(emptyList(),
            //Eliminar
            { producto ->
                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar Producto")
                    .setMessage("¿Está seguro de que desea eliminar ${producto.nombre_producto}?")
                    .setPositiveButton("Eliminar") { _, _ ->
                        lifecycleScope.launch {
                            try {
                                productoRepository.eliminarProducto(producto.id_producto!!)
                                cargarProductos()
                                Toast.makeText(requireContext(), "Producto eliminado", Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                                e.printStackTrace()
                                Toast.makeText(requireContext(), "Error al eliminar", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    .setNegativeButton("Cancelar", null)
                    .show()
            },//Editar
            { producto ->
                val fragment = ProductosAddFragment()
                val bundle = Bundle()
                val infoColor = producto.producto_Color?.firstOrNull()

                bundle.putString("id_producto", producto.id_producto)
                bundle.putString("nombre_producto", producto.nombre_producto)
                bundle.putString("codigo_producto", producto.codigo_producto)
                bundle.putString("descripcion", producto.descripcion)
                bundle.putString("foto_producto", producto.foto_producto)
                bundle.putString("id_categoria", producto.id_categoria_FK)
                
                // Pasar datos de color y stock
                bundle.putDouble("precio", infoColor?.precio ?: 0.0)
                bundle.putInt("stock", infoColor?.stock ?: 0)
                bundle.putString("id_color", infoColor?.id_color_FK)

                fragment.arguments = bundle
                (activity as? AdminActivity)?.cargarFragment(fragment, true)
            }
        )
        rvProducto.adapter = productoAdminAdapter
        cargarProductos()

        //me redirecciona a otro fragment
        view.findViewById<View>(R.id.btnAgregar_producto).setOnClickListener {
            (activity as? AdminActivity)?.cargarFragment(ProductosAddFragment(),  true)
        }

        return view
    }


}


