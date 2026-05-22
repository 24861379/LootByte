package com.example.lootbyte.Repository

import android.provider.SyncStateContract
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.query.Columns

class UsuarioRepository {
    suspend fun obtenerUsuarios(): List<Usuario> {
        return SupabaseClient.client
            .from("usuario")
            .select(Columns.raw("*, rol(*)"))
            .decodeList<Usuario>()
    }

    suspend fun insertUsuario(usuario: Usuario) {
         SupabaseClient.client
            .from("usuario")
            .insert(usuario)
    }

    suspend fun deleteUsuario(id_usuario: String) {
        SupabaseClient.client
            .from("usuario")
            .delete {
                filter {
                    eq("id_usuario", id_usuario)
                }
            }
    }

    suspend fun actualizarUsuario(usuario: Usuario) {
        SupabaseClient.client
            .from("usuario")
            .update({
                set("nombre_completo", usuario.nombre_completo)
                set("correo", usuario.correo)
                set("password_hash", usuario.password_hash)
                set("celular", usuario.celular)
                set("direccion", usuario.direccion)
                set("ciudad", usuario.ciudad)
                set("foto_perfil", usuario.foto_perfil)
                set("id_rol_FK", usuario.id_rol_FK)
            }){
                filter {
                    eq("id_usuario", usuario.id_usuario!!)
                }
            }
    }
}