package com.example.lootbyte.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Producto(
    val id_producto: String? = null,
    val nombre_producto: String = "",
    val codigo_producto: String = "",
    val descripcion: String = "",
    val foto_producto: String = "",
    val id_categoria_FK: String? = null,
    @Transient
    @SerialName("producto_color")
    val producto_Color: List<ProductoColor>? = null
) {
    val stockTotal: Int
        get() = producto_Color?.sumOf { it.stock } ?:0
}
