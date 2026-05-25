package com.example.lootbyte.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.lootbyte.R
import com.example.lootbyte.UI.MainMenu.Admin.Pedido

class PedidosAdapter(private val pedidos: List<Pedido>) :
    RecyclerView.Adapter<PedidosAdapter.PedidoViewHolder>() {

    class PedidoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvUsuario: TextView = view.findViewById(R.id.tvUsuario)
        val tvEstado: TextView = view.findViewById(R.id.tvEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PedidoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pedido, parent, false)
        return PedidoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PedidoViewHolder, position: Int) {
        val pedido = pedidos[position]
        holder.tvUsuario.text = pedido.usuario
        holder.tvEstado.text = "Ver pedido(s)"
    }

    override fun getItemCount() = pedidos.size
}