package com.example.lootbyte.Model

data class Producto(
    val id: Int,
    val nombre: String,
    val precio: Double,
    val imagen: Int,
    val descripcion: String,
    val detalles: List<String>,
    val review: List<String>,
)
