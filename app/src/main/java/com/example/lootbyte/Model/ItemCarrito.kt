package com.example.lootbyte.Model

//ESTE ES EL MODELO DE LA UI
data class ItemCarrito(
    val id_detalle_carrito: String? = null,
    val producto: Producto,
    val productoColor: ProductoColor,
    var cantidad: Int = 1,
    var seleccionado: Boolean = false
)
