package com.example.lootbyte.Adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.Model.Producto
import com.example.lootbyte.R
import java.text.NumberFormat
import java.util.Locale

class productoAdminAdapter (private val listaProductos: List<Producto>, private val onCambio: ()-> Unit) : RecyclerView.Adapter<productoAdminAdapter.ProductoViewHolder>() {

    private val formatCOP = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val producto : ImageView = itemView.findViewById(R.id.img_producto)
        val nombre : TextView = itemView.findViewById(R.id.tv_Nombre_Producto)
        val precio : TextView = itemView.findViewById(R.id.tv_precio)
        val cantidad : TextView = itemView.findViewById(R.id.tv_cantidades_disponibles)
        val btnCRUD : ImageView = itemView.findViewById(R.id.btn_CRUD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(itemView)
    }

    override fun getItemCount(): Int = listaProductos.size

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]
        holder.producto.setImageResource(producto.imagen)
        holder.nombre.text = producto.nombre
        holder.precio.text = "${formatCOP.format(producto.precio)}"
        holder.cantidad.text = producto.cantidad.toString()
        holder.btnCRUD.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.popup_menu_crud, popup.menu)

            val deleteItem = popup.menu.findItem(R.id.eliminar)
            val redText = SpannableString("Eliminar")
            redText.setSpan(android.text.style.ForegroundColorSpan(android.graphics.Color.RED), 0, redText.length, 0)
            deleteItem.title= redText

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.editar -> true
                    R.id.actualizar -> true
                    R.id.eliminar -> true
                    else -> false
                }

            }
            popup.show()
        }

    }

}
