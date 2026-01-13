package com.example.softcomchallengejunior.data.repository

import com.example.softcomchallengejunior.data.remote.dto.CategoryDto
import com.example.softcomchallengejunior.data.remote.dto.ProductDto
import com.example.softcomchallengejunior.domain.repository.ProductRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.postgrest.postgrest
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val supabase: SupabaseClient // O Hilt vai entregar isso aqui pronto!
) : ProductRepository {

    override suspend fun getCategories(): List<CategoryDto> {
        // Vai na tabela "categories" e transforma o JSON na nossa lista de DTOs
        return supabase.from("categories").select().decodeList<CategoryDto>()
    }

    override suspend fun getProducts(): List<ProductDto> {
        // Vai na tabela "products" e busca tudo
        return supabase.from("products").select().decodeList<ProductDto>()
    }
}