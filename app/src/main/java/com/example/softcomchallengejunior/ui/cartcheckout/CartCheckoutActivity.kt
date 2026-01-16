package com.example.softcomchallengejunior.ui.order.checkout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import com.example.softcomchallengejunior.ui.cartcheckout.CartCheckoutScreen
import com.example.softcomchallengejunior.ui.theme.SoftcomChallengeJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartCheckoutActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recupera o valor que veio da CartScreen
        val totalPaid = intent.getDoubleExtra("TOTAL_PAID", 0.0)

        enableEdgeToEdge()
        setContent {
            SoftcomChallengeJuniorTheme {
                // Passamos o valor direto para a Screen
                CartCheckoutScreen(
                    totalPaid = totalPaid,
                    onNewOrderClick = {
                        // Fecha tudo e volta pra Home (MainActivity)
                        finish()
                    }
                )
            }
        }
    }
}