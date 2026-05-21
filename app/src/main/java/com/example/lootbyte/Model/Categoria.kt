package com.example.lootbyte.Model

import kotlinx.serialization.Serializable

@Serializable
data class Categoria(
    val id_categoria: String? = null,
    val categoria_producto: String
)
