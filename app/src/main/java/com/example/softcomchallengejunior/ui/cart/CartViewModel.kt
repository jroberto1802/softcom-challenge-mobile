package com.example.softcomchallengejunior.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softcomchallengejunior.data.local.entities.CartItemEntity
import com.example.softcomchallengejunior.domain.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CartUiEvent {
    data class NavigateToCheckout(val totalPrice: Double) : CartUiEvent
}
@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel() {
    private val _uiEvent = Channel<CartUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
    val cartItems: StateFlow<List<CartItemEntity>> = repository.getCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val totalPrice: StateFlow<Double> = cartItems.map { items ->
        items.sumOf { it.price * it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val totalItems: StateFlow<Int> = cartItems.map { items ->
        items.sumOf { it.quantity }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    fun updateQuantity(item: CartItemEntity, increment: Boolean) {
        viewModelScope.launch {
            val newQuantity = if (increment) item.quantity + 1 else item.quantity - 1
            if (newQuantity > 0) {
                repository.addToCart(item.copy(quantity = newQuantity))
            } else {
                repository.removeFromCart(item)
            }
        }
    }

    fun removeItem(item: CartItemEntity) {
        viewModelScope.launch {
            repository.removeFromCart(item)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }

    fun onCheckoutClicked() {
        viewModelScope.launch {
            // Se o carrinho estiver vazio, podemos evitar a navegação
            if (cartItems.value.isNotEmpty()) {
                _uiEvent.send(CartUiEvent.NavigateToCheckout(totalPrice.value))
            }
        }
    }
}
