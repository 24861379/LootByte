package com.example.lootbyte.Model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int = 1,
    var seleccionado: Boolean = false
)
