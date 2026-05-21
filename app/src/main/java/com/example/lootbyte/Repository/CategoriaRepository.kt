package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Categoria
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from

class CategoriaRepository {
    suspend fun obtenerCategorias(): List<Categoria>{
        return SupabaseClient.client
            .from("categoria")
            .select()
            .decodeList<Categoria>()
    }
}
