package com.example.lootbyte.Model

import kotlinx.serialization.Serializable

@Serializable
data class Color(
    val id_color: String? = null,
    val nombre_color: String
)
