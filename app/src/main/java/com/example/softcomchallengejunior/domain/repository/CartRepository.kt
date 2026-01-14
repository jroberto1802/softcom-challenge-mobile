package com.example.softcomchallengejunior.domain.repository

import com.example.softcomchallengejunior.data.local.entities.CartItemEntity
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    // Retorna um fluxo constante dos itens no banco
    fun getCartItems(): Flow<List<CartItemEntity>>

    // Funções de suspensão para operações de escrita
    suspend fun addToCart(item: CartItemEntity)
    suspend fun removeFromCart(item: CartItemEntity)
    suspend fun clearCart()
}