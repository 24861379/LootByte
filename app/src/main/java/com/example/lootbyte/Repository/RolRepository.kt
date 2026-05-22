package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Rol
import com.example.lootbyte.Model.Usuario
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from

class RolRepository {
    suspend fun obtenerRoles(): List<Rol> {
        return SupabaseClient.client
            .from("rol")
            .select()
            .decodeList<Rol>()
    }
}