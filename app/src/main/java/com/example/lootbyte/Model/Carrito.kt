package com.example.lootbyte.Model

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class Carrito(
    val id_carrito: String? = null,
    val fecha_creacion: Instant? = null,
    val id_usuario_FK: String? = null,

    val usuario: Usuario? = null
)
