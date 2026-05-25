package com.example.lootbyte.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.R
import com.example.lootbyte.databinding.ItemProductoClienteBinding
import java.text.NumberFormat
import java.util.Locale

class ProductoClienteAdapter(
    private var listaProductos: List<Producto>,
    private val onClick: (Producto) -> Unit
) : RecyclerView.Adapter<ProductoClienteAdapter.ProductoViewHolder>() {

    private val formatCOP =
        NumberFormat.getCurrencyInstance(Locale("es", "CO"))

    inner class ProductoViewHolder(
        val binding: ItemProductoClienteBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ProductoViewHolder {

        val binding =
            ItemProductoClienteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ProductoViewHolder(binding)
    }

    override fun getItemCount(): Int =
        listaProductos.size

    override fun onBindViewHolder(
        holder: ProductoViewHolder,
        position: Int
    ) {

        val producto = listaProductos[position]

        holder.binding.imgProducto.load(
            producto.foto_producto
        ) {
            crossfade(true)
            placeholder(R.drawable.image_regular_full)
            error(R.drawable.triangle_exclamation_solid_full)
        }

        holder.binding.tvNombreProducto.text =
            producto.nombre_producto

        holder.binding.tvPrecio.text =
            formatCOP.format(
                producto.producto_Color
                    ?.firstOrNull()
                    ?.precio ?: 0.0
            )

        holder.binding.btnAgregarCarrito.setOnClickListener {

            onClick(producto)

        }
    }

    fun actualizarLista(
        nuevaLista: List<Producto>
    ) {

        listaProductos = nuevaLista

        notifyDataSetChanged()
    }
}