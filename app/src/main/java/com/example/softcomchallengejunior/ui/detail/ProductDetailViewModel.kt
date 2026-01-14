package com.example.softcomchallengejunior.ui.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softcomchallengejunior.data.local.entities.CartItemEntity
import com.example.softcomchallengejunior.data.remote.dto.ProductDto
import com.example.softcomchallengejunior.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val cartRepository: CartRepository
) : ViewModel() {

    private val productJson: String? = savedStateHandle["PRODUCT_JSON"]
    val product: ProductDto? = productJson?.let { Json.decodeFromString<ProductDto>(it) }

    private val _quantity = MutableStateFlow(1)
    val quantity: StateFlow<Int> = _quantity.asStateFlow()

    private val _observation = MutableStateFlow("")
    val observation: StateFlow<String> = _observation.asStateFlow()

    // Evento para sinalizar que o produto foi adicionado ao carrinho
    private val _productAddedToCartEvent = MutableSharedFlow<Unit>()
    val productAddedToCartEvent: SharedFlow<Unit> = _productAddedToCartEvent.asSharedFlow()

    fun incrementQuantity() {
        _quantity.value++
    }

    fun decrementQuantity() {
        if (_quantity.value > 1) {
            _quantity.value--
        }
    }

    fun updateObservation(newObservation: String) {
        if (newObservation.length <= 100) {
            _observation.value = newObservation
        }
    }

    fun addItemToCart() {
        viewModelScope.launch {
            product?.let { p ->
                val cartItem = CartItemEntity(
                    productId = p.id,
                    name = p.name,
                    imageUrl = p.imageUrl,
                    price = if (p.isPromotion && p.promotionPrice != null) p.promotionPrice else p.price,
                    quantity = _quantity.value,
                    observation = _observation.value,
                    category = p.categoryId
                )
                cartRepository.addToCart(cartItem)
                _productAddedToCartEvent.emit(Unit) // Emite o evento após adicionar
            }
        }
    }
}
