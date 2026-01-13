package com.example.softcomchallengejunior.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient {
        return createSupabaseClient(
            supabaseUrl = "https://tqcxjulnbhouqpbijtwl.supabase.co",
            supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InRxY3hqdWxuYmhvdXFwYmlqdHdsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjgxNTY0MzcsImV4cCI6MjA4MzczMjQzN30.zr1adsivWsZ5REeB4BbYZL7LpBiVTVxOLk4F42EeYhU"
        ) {
            // Se 'serializer' der erro, use 'defaultSerializer' ou configure assim:
            install(Postgrest)

            // Esta é a forma mais segura de configurar o JSON globalmente no Supabase
            defaultSerializer = KotlinXSerializer(Json {
                ignoreUnknownKeys = true
                coerceInputValues = true
                explicitNulls = false
            })
        }
    }
}