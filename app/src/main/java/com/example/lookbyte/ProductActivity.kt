package com.example.lookbyte

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProductActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val btnCarrito = findViewById<Button>(R.id.btnCarrito)

        btnCarrito.setOnClickListener {

            Toast.makeText(
                this,
                "Producto agregado al carrito exitosamente",
                Toast.LENGTH_SHORT
            ).show()

        }
    }
}