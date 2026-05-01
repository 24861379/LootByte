package com.example.lootbyte.Model

data class ItemCarrito(
    val producto: Producto,
    var cantidad: Int,
    var seleccionado: Boolean = true
)
