package com.example.lootbyte.Model

import kotlinx.serialization.Serializable

@Serializable
data class Rol(
    val id_rol: String? = null,
    val nombre_rol: String
)
