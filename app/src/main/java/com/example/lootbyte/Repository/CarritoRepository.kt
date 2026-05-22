package com.example.lootbyte.Repository

import com.example.lootbyte.Model.DetalleCarrito
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class CarritoRepository {
    suspend fun obtenerDetalleCarrito(idCarrito: Int): List<DetalleCarrito> {
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
                    eq("id_carrito", idCarrito)
                }
            }
            .decodeList<DetalleCarrito>()
    }

    suspend fun actualizarCantidad(
        idDetalleCarrito: String,
        cantidad: Int
    ) {

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