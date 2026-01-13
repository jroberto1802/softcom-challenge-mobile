package com.example.softcomchallengejunior.domain.repository

import com.example.softcomchallengejunior.data.remote.dto.CategoryDto
import com.example.softcomchallengejunior.data.remote.dto.ProductDto

interface ProductRepository {
    suspend fun getCategories(): List<CategoryDto>
    suspend fun getProducts(): List<ProductDto>
}