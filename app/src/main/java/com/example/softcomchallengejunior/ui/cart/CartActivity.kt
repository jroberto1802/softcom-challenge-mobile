package com.example.softcomchallengejunior.ui.cart

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.softcomchallengejunior.ui.theme.SoftcomChallengeJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftcomChallengeJuniorTheme {
                CartScreen(
                    onBackClick = { finish() },
                    onCheckoutClick = {
                        // Lógica de finalizar pedido
                    }
                )
            }
        }
    }
}
