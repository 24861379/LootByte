package com.example.lootbyte

import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionSource
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import kotlinx.serialization.json.Json

object SupabaseClient {
    val client = createSupabaseClient(
        supabaseUrl = "https://svldscoxhoojmkhtbzbq.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InN2bGRzY294aG9vam1raHRiemJxIiwicm9sZSI6ImFub24iLCJpYXQiOjE3Nzg3NzcwMTcsImV4cCI6MjA5NDM1MzAxN30.F2Sfsrzcsxbb40evWuGzNDjFTeGYv_lyTMT_7VIcU0A"
    ){
        install(Postgrest)
        install(Auth)
        install(Storage)
        install(Postgrest)
        defaultSerializer = KotlinXSerializer(Json{
            ignoreUnknownKeys = true
            coerceInputValues = true
            encodeDefaults = false
        })
    }
}