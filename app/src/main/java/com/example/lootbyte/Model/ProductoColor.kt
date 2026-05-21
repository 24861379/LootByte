package com.example.lootbyte.Model

import kotlinx.serialization.Serializable

@Serializable
data class ProductoColor(
    val id_producto_color: String? = null,
    val stock: Int = 0,
    val precio: Double = 0.0,
    val id_producto_FK: String? = null,
    val id_color_FK: String? = null,
//    val id_oferta_FK: String? = null

    val producto: Producto? = null,
    val color: Color? = null
)
