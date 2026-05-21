package com.example.lootbyte.UI.MainMenu.Admin

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.lifecycleScope
import com.example.lootbyte.Model.Categoria
import com.example.lootbyte.Model.Color
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.Model.ProductoColor
import com.example.lootbyte.R
import com.example.lootbyte.Repository.CategoriaRepository
import com.example.lootbyte.Repository.ColorRepository
import com.example.lootbyte.Repository.ProductoRepository
import com.example.lootbyte.SupabaseClient
import com.google.android.material.button.MaterialButton
import io.github.jan.supabase.storage.storage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class ProductosAddFragment : Fragment() {
    private lateinit var listaCategorias: List<Categoria>
    private lateinit var listaColores: List<Color>
    private lateinit var spCategorias: Spinner
    private lateinit var spColores: Spinner

    private val categoriaRepository = CategoriaRepository()
    private val colorRepository = ColorRepository()
    private val productoRepository = ProductoRepository()
    private var imageUri: Uri?= null
    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
        }
    }

    private var id_Producto: String? = null

    override fun onResume() {
        super.onResume()
        (activity as? AdminActivity)?.actualizarHeader(R.layout.header_simple)
        
        val titulo = if (id_Producto != null) "Editar producto" else "Crear producto"
        requireActivity().findViewById<TextView>(R.id.tvTitulo)?.text = titulo
    }

    private fun cargarCategorias() {
        lifecycleScope.launch {
            listaCategorias = categoriaRepository.obtenerCategorias()
            val nombresCategorias = listaCategorias.map { it.categoria_producto }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresCategorias)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spCategorias.adapter = adapter

            arguments?.getString("id_categoria")?.let { idCat ->
                val index = listaCategorias.indexOfFirst { it.id_categoria == idCat }
                if (index != -1) spCategorias.setSelection(index)
            }
        }
    }

    private fun cargarColores() {
        lifecycleScope.launch {
            listaColores = colorRepository.obtenerColores()
            val nombresColores = listaColores.map { it.nombre_color }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, nombresColores)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spColores.adapter = adapter

            // Seleccionar color al editar
            arguments?.getString("id_color")?.let { idCol ->
                val index = listaColores.indexOfFirst { it.id_color == idCol }
                if (index != -1) spColores.setSelection(index)
            }
        }
    }

    private suspend fun subirImagen(uri: Uri): String {
        val bytes = requireContext().contentResolver.openInputStream(uri)?.readBytes()
        val nombreArchivo = "${UUID.randomUUID()}.jpg"
        SupabaseClient.client.storage.from("img_productos").upload(path = nombreArchivo, data = bytes!!)
        return SupabaseClient.client.storage.from("img_productos").publicUrl(nombreArchivo)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_productos_add, container, false)
        
        // Referencias de UI
        val etNombre = view.findViewById<EditText>(R.id.et_Nombre_producto)
        val etCodigo = view.findViewById<EditText>(R.id.et_codigo_producto)
        val etPrecio = view.findViewById<EditText>(R.id.et_precio_producto)
        val etStock = view.findViewById<EditText>(R.id.et_stock_producto)
        val etDescripcion = view.findViewById<EditText>(R.id.et_descripcion_producto)
        val btnAgregar = view.findViewById<MaterialButton>(R.id.btn_agregar_Producto)
        val btnFotoProducto = view.findViewById<View>(R.id.btn_foto_producto)
        spCategorias = view.findViewById(R.id.sp_categoria_producto)
        spColores = view.findViewById(R.id.sp_colores_producto)

        btnFotoProducto.setOnClickListener { pickImage.launch("image/*") }
        
        id_Producto = arguments?.getString("id_producto")
        cargarCategorias()
        cargarColores()

        // Modo Edición: Llenar campos
        if (id_Producto != null) {
            btnAgregar.text = "Editar Producto"
            etNombre.setText(arguments?.getString("nombre_producto"))
            etCodigo.setText(arguments?.getString("codigo_producto"))
            etDescripcion.setText(arguments?.getString("descripcion"))
            etPrecio.setText(arguments?.getDouble("precio", 0.0).toString())
            etStock.setText(arguments?.getInt("stock", 0).toString())
        }

        btnAgregar.setOnClickListener {
            val nombre = etNombre.text.toString()
            val codigo = etCodigo.text.toString()
            val precioStr = etPrecio.text.toString()
            val stockStr = etStock.text.toString()
            val descripcion = etDescripcion.text.toString()

            if (nombre.isEmpty() || codigo.isEmpty() || precioStr.isEmpty() || stockStr.isEmpty() || descripcion.isEmpty()) {
                Toast.makeText(requireContext(), "Complete todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val urlImagen = if (imageUri != null) subirImagen(imageUri!!) 
                                   else arguments?.getString("foto_producto") ?: ""
                    
                    val precio = precioStr.toDouble()
                    val stock = stockStr.toInt()
                    val categoriaId = listaCategorias[spCategorias.selectedItemPosition].id_categoria
                    val colorId = listaColores[spColores.selectedItemPosition].id_color

                    if (id_Producto == null) {
                        // CREAR
                        val producto = Producto(
                            nombre_producto = nombre,
                            codigo_producto = codigo,
                            descripcion = descripcion,
                            foto_producto = urlImagen,
                            id_categoria_FK = categoriaId
                        )
                        val insertado = productoRepository.insertarProductoRetornando(producto)
                        productoRepository.insertarProductoColor(ProductoColor(
                            id_producto_FK = insertado.id_producto,
                            id_color_FK = colorId,
                            stock = stock,
                            precio = precio
                        ))
                        Toast.makeText(requireContext(), "Producto creado", Toast.LENGTH_SHORT).show()
                    } else {
                        // ACTUALIZAR
                        productoRepository.actualizarProducto(Producto(
                            id_producto = id_Producto,
                            nombre_producto = nombre,
                            codigo_producto = codigo,
                            descripcion = descripcion,
                            foto_producto = urlImagen,
                            id_categoria_FK = categoriaId
                        ))
                        productoRepository.actualizarProductoColor(ProductoColor(
                            id_producto_FK = id_Producto,
                            id_color_FK = colorId,
                            stock = stock,
                            precio = precio
                        ))
                        Toast.makeText(requireContext(), "Producto actualizado", Toast.LENGTH_SHORT).show()
                    }
                    requireActivity().onBackPressedDispatcher.onBackPressed()
                } catch (e: Exception) {
                    android.util.Log.e("SUPABASE_ERROR", "Error: ${e.message}")
                    Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
        }
        return view
    }
}
