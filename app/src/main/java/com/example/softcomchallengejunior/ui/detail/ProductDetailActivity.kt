package com.example.softcomchallengejunior.ui.detail

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.softcomchallengejunior.ui.theme.SoftcomChallengeJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailActivity : ComponentActivity() {

    private val viewModel: ProductDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            SoftcomChallengeJuniorTheme {
                val product = viewModel.product
                if (product != null) {
                    ProductDetailScreen(
                        viewModel = viewModel,
                        onBackClick = { finish() }
                    )
                }
            }
        }
    }
}