package com.example.lootbyte.Adapter

import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.R

public class UsuarioAdapter (private val usuarios: List<Usuario>, private val onEliminar: (Usuario)-> Unit, private val onEditar: (Usuario)-> Unit) : RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder>() {
    inner class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagenUsuario: ImageView = itemView.findViewById(R.id.img_usuario)
        val nombreUsuario: TextView = itemView.findViewById(R.id.tv_Nombre_usuario)
        val rolUsuario: TextView = itemView.findViewById(R.id.tv_Rol)
        val btn_CRUD: ImageView = itemView.findViewById(R.id.btn_CRUD)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(itemView)
    }

    override fun getItemCount(): Int = usuarios.size

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuarios[position]
        holder.imagenUsuario.load(usuario.foto_perfil)
        holder.nombreUsuario.text = usuario.nombre_completo
        holder.rolUsuario.text = usuario.rol?.nombre_rol?:"Sin rol"

        holder.btn_CRUD.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view)
            popup.menuInflater.inflate(R.menu.popup_menu_crud, popup.menu)

            val deleteItem = popup.menu.findItem(R.id.eliminar)
            val redText = SpannableString("Eliminar")
            redText.setSpan(android.text.style.ForegroundColorSpan(android.graphics.Color.RED), 0, redText.length, 0)
            deleteItem.title= redText

            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.editar -> {
                        onEditar(usuario)
                        true
                    }
                    R.id.eliminar -> {
                        onEliminar(usuario)
                        true
                    }
                    else -> false
                }

            }
            popup.show()
        }
    }
}
