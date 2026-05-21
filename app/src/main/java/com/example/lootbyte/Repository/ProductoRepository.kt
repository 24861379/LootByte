package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Producto
import com.example.lootbyte.Model.ProductoColor
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class ProductoRepository {

    suspend fun obtenerProductos(): List<Producto>{
        return SupabaseClient.client
            .from("producto")
            .select(Columns.raw("*, producto_color(stock, precio)"))
            .decodeList<Producto>()
    }

    suspend fun insertarProductoRetornando(producto: Producto): Producto {

        return SupabaseClient.client
            .from("producto")
            .insert(producto) {
                select()
            }
            .decodeSingle()
    }
    suspend fun insertarProductoColor(productoColor: ProductoColor) {

        SupabaseClient.client
            .from("producto_color")
            .insert(productoColor)
    }

    suspend fun eliminarProducto(id_producto: String){

        // Primero elimina variantes(colores) relacionados pero no los colores
        SupabaseClient.client
            .from("producto_color")
            .delete {
                filter {
                    eq("id_producto_FK", id_producto)
                }
            }

        // Luego elimina producto
        SupabaseClient.client
            .from("producto")
            .delete {
                filter{
                    eq("id_producto", id_producto)
                }
            }
    }

    suspend fun actualizarProducto(producto: Producto) {
        SupabaseClient.client
            .from("producto")
            .update({
                set("nombre_producto", producto.nombre_producto)
                set("codigo_producto", producto.codigo_producto)
                set("descripcion", producto.descripcion)
                set("foto_producto", producto.foto_producto)
                set("id_categoria_FK", producto.id_categoria_FK)
            }) {
                filter {
                    eq("id_producto", producto.id_producto!!)
                }
            }
    }

    suspend fun actualizarProductoColor(productoColor: ProductoColor) {
        SupabaseClient.client
            .from("producto_color")
            .update({
                set("stock", productoColor.stock)
                set("precio", productoColor.precio)
                set("id_color_FK", productoColor.id_color_FK)
            }) {
                filter {
                    eq("id_producto_FK", productoColor.id_producto_FK!!)
                }
            }
    }

}