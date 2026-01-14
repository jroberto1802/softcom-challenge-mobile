package com.example.softcomchallengejunior.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartItemEntity(
    @PrimaryKey val productId: Long,
    val name: String,
    val price: Double,
    val quantity: Int,
    val imageUrl: String?,
    val category: Long,
    val observation: String?
)