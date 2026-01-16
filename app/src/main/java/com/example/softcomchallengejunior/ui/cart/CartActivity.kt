package com.example.softcomchallengejunior.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.softcomchallengejunior.ui.theme.SoftcomChallengeJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftcomChallengeJuniorTheme {
                val viewModel: CartViewModel = hiltViewModel()
                CartScreen(
                    onBackClick = { finish() },
                    onCheckoutClick = {
                        viewModel.clearCart() // Limpa o carrinho após o checkout
                        finish() // Fecha a tela do carrinho após o checkout
                        viewModel.onCheckoutClicked() // Navega para a tela de checkout
                    }
                )
            }
        }
    }
}