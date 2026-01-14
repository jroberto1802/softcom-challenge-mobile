package com.example.softcomchallengejunior.data.repository

import com.example.softcomchallengejunior.data.local.dao.CartDao
import com.example.softcomchallengejunior.data.local.entities.CartItemEntity
import com.example.softcomchallengejunior.domain.repository.CartRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao
) : CartRepository {

    override fun getCartItems(): Flow<List<CartItemEntity>> {
        return cartDao.getAllCartItems()
    }

    override suspend fun addToCart(item: CartItemEntity) {
        cartDao.insertOrUpdate(item)
    }

    override suspend fun removeFromCart(item: CartItemEntity) {
        cartDao.delete(item)
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }
}