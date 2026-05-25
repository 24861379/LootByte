package com.example.lootbyte.Model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class DetalleCarrito(
    val id_detalle_carrito: String? = null,
    val id_carrito_FK: String? = null,
    val id_producto_color_Fk: String? = null,
    val fecha_creacion: Instant? = null,
    val cantidad: Int,

    val producto_Color: ProductoColor? = null
)
