package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Carrito
import com.example.lootbyte.Model.DetalleCarrito
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class CarritoRepository {

    suspend fun agregarAlCarrito(detalleCarrito: DetalleCarrito) {
        SupabaseClient.client
            .from("detalle_carrito")
            .insert(detalleCarrito)
    }

    suspend fun obtenerDetalleCarrito(idCarrito: String): List<DetalleCarrito> {
        return SupabaseClient.client
            .from("detalle_carrito")
            .select(
                Columns.raw(
                    """
                *,
                producto_color (
                    *,
                    producto (*),
                    color (*)
                )
                """.trimIndent()
                )
            ) {
                filter {
                    eq("id_carrito_FK", idCarrito)
                }
            }
            .decodeList<DetalleCarrito>()
    }

    suspend fun obtenerOCrearCarrito(idUsuario: String): Carrito {
        // Buscar carrito existente
        val carritoExistente = SupabaseClient.client
            .from("carrito")
            .select {
                filter {
                    eq("id_usuario_FK", idUsuario)
                }
            }
            .decodeSingleOrNull<Carrito>()

        if (carritoExistente != null) {
            return carritoExistente
        }

        // Crear carrito nuevo
        val nuevoCarrito = Carrito(id_usuario_FK = idUsuario)
        return SupabaseClient.client
            .from("carrito")
            .insert(nuevoCarrito) {
                select()
            }
            .decodeSingle<Carrito>()
    }

    suspend fun actualizarCantidad(idDetalleCarrito: String, cantidad: Int) {
        SupabaseClient.client
            .from("detalle_carrito")
            .update({
                set("cantidad", cantidad)
            }) {
                filter {
                    eq("id_detalle_carrito", idDetalleCarrito)
                }
            }
    }

    suspend fun eliminarDetalleCarrito(idDetalleCarrito: String) {
        SupabaseClient.client
            .from("detalle_carrito")
            .delete {
                filter {
                    eq("id_detalle_carrito", idDetalleCarrito)
                }
            }
    }
}
