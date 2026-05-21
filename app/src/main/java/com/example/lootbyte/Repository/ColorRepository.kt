package com.example.lootbyte.Repository

import com.example.lootbyte.Model.Color
import com.example.lootbyte.SupabaseClient
import io.github.jan.supabase.postgrest.from

class ColorRepository {
    suspend fun obtenerColores(): List<Color>{
        return SupabaseClient.client
            .from("color")
            .select()
            .decodeList<Color>()
    }
}
