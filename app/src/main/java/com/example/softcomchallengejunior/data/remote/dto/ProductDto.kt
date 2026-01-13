package com.example.softcomchallengejunior.data.remote.dto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("description") val description: String? = null,
    @SerialName("price") val price: Double,
    @SerialName("is_promotion") val isPromotion: Boolean,
    @SerialName("promotion_price") val promotionPrice: Double? = null,
    @SerialName("image_url") val imageUrl: String? = null,
    @SerialName("category_id") val categoryId: Long
)