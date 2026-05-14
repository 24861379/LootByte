package com.example.lootbyte.UI.MainMenu.Cliente

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lootbyte.R

class ProductoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnCarrito = view.findViewById<Button>(R.id.btnCarrito)

        btnCarrito.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Producto agregado al carrito exitosamente",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}