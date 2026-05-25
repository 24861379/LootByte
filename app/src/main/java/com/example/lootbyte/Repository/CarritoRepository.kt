package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Carrito
import com.example.lootbyte.Model.DetalleCarrito
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class CarritoRepository {

    suspend fun agregarAlCarrito(detalleCarrito: DetalleCarrito) {
        val datos = buildJsonObject {
            put("id_carrito_FK", detalleCarrito.id_carrito_FK)
            put("id_producto_color_Fk", detalleCarrito.id_producto_color_Fk)
            put("cantidad", detalleCarrito.cantidad)
        }
        
        SupabaseClient.client
            .from("detalle_carrito")
            .insert(datos)
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

        // Crear carrito nuevo usando buildJsonObject para evitar errores de serialización
        val datos = buildJsonObject {
            put("id_usuario_FK", idUsuario)
        }

        return SupabaseClient.client
            .from("carrito")
            .insert(datos) {
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
