package com.example.lootbyte.Model

import kotlinx.serialization.Serializable

@Serializable
data class Usuario(
    val id_usuario: String? = null,
    val nombre_completo: String = "",
    val correo: String = "",
    val password_hash: String = "",
    val celular: String = "",
    val direccion: String = "",
    val ciudad: String = "",
    val foto_perfil: String = "",
    val id_rol_FK: String? = null,
    val rol: Rol? = null
)
