package com.example.lootbyte.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Carrito(
    @SerialName("id_carrito")
    val id_carrito: String? = null,
    
    @SerialName("fecha_creacion")
    val fecha_creacion: String? = null,
    
    @SerialName("id_usuario_FK")
    val id_usuario_FK: String? = null,

    val usuario: Usuario? = null
)
