package com.example.lootbyte.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DetalleCarrito(
    @SerialName("id_detalle_carrito")
    val id_detalle_carrito: String? = null,
    
    @SerialName("id_carrito_FK")
    val id_carrito_FK: String? = null,
    
    @SerialName("id_producto_color_Fk")
    val id_producto_color_Fk: String? = null,
    
    @SerialName("fecha_creacion")
    val fecha_creacion: String? = null,

    @SerialName("cantidad")
    val cantidad: Int,

    @SerialName("producto_color")
    val producto_Color: ProductoColor? = null
)
