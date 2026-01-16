package com.example.softcomchallengejunior.ui.cartcheckout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface CartCheckoutUiEvent {
    data class CartCheckout(val totalPaid: Double) : CartCheckoutUiEvent
}
@HiltViewModel
class CartCheckoutViewModel @Inject constructor() : ViewModel() {
    // Função para gerar numero do pedido aleatório
    fun generateOrderNumber() = "#PF2026${(100..999).random()}"
}
