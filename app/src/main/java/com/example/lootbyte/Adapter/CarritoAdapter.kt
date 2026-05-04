package com.example.lootbyte.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.NumberFormat
import java.util.Locale
import com.example.lootbyte.Model.ItemCarrito
import com.example.lootbyte.R


public class CarritoAdapter (private val items:List<ItemCarrito>, private val onCambio: ()-> Unit) : RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView) {
        val imagenProducto: ImageView = itemView.findViewById(R.id.img_Producto)
        val nombreProducto: TextView = itemView.findViewById(R.id.tv_Nombre)
        val precioProducto: TextView = itemView.findViewById(R.id.tv_Precio)
        val spinnerCantidad: Spinner = itemView.findViewById(R.id.sp_Cantidad)

        val checkProducto: CheckBox = itemView.findViewById(R.id.checkBox)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(itemView)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val formartCOP = NumberFormat.getCurrencyInstance(Locale("es", "CO"))

        val item = items[position]

        val cantidades = arrayOf("1 unidad", "2 unidades", "3 unidades", "4 unidades", "5 unidades", "6 unidades", "7 unidades", "8 unidades", "9 unidades", "10 unidades", "11 unidades", "12 unidades", "13 unidades", "14 unidades", "15 unidades", "16 unidades", "17 unidades")
        val adapterSpinner = ArrayAdapter(holder.itemView.context, android.R.layout.simple_spinner_item, cantidades)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        holder.nombreProducto.text = item.producto.nombre
        holder.precioProducto.text = "${formartCOP.format(item.producto.precio)}"
        holder.imagenProducto.setImageResource(item.producto.imagen)
        holder.spinnerCantidad.adapter = adapterSpinner
        holder.spinnerCantidad.setSelection(item.cantidad - 1)
        holder.checkProducto.isChecked = item.seleccionado

        holder.checkProducto.setOnCheckedChangeListener { _, isChecked ->
            item.seleccionado = isChecked
            onCambio()
        }

        holder.spinnerCantidad.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                item.cantidad = position + 1
                onCambio()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }
}
