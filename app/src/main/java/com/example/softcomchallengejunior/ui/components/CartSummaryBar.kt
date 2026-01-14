package com.example.softcomchallengejunior.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softcomchallengejunior.ui.theme.Poppins

@Composable
fun CartSummaryBar(
    totalItems: Int,
    totalPrice: Double,
    onViewCartClick: () -> Unit
) {
    // Só exibe se houver itens no carrinho
    if (totalItems > 0) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding para o card externo
        ) {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
                shadowElevation = 8.dp // Elevação para o efeito de sombra
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // Ícone e Detalhes do Carrinho
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            modifier = Modifier.size(40.dp),
                            shape = CircleShape,
                            color = Color(0x1AFF6B9D) // Rosa claro com opacidade
                        ) {
                            Icon(
                                imageVector = Icons.Default.ShoppingCart,
                                contentDescription = "Carrinho de compras",
                                tint = Color(0xFFFF6B9D), // Rosa
                                modifier = Modifier.padding(8.dp)
                            )
                        }

                        Column {
                            Text(
                                text = "$totalItems ${if (totalItems > 1) "produtos" else "produto"}",
                                color = Color(0xFF6B7280), // Cinza para o texto de itens
                                fontSize = 14.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = "R$ ${String.format("%.2f", totalPrice)}",
                                color = Color(0xFF1F2937), // Azul escuro para o preço
                                fontSize = 16.sp,
                                fontFamily = Poppins,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    // Botão "Ver carrinho"
                    Button(
                        onClick = onViewCartClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent), // Transparente para usar o Brush
                        shape = RoundedCornerShape(24.dp), // Shape de pílula
                        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp)
                    ) {
                        // Camada de Brush para o gradiente de fundo do botão
                        val gradientBrush = Brush.horizontalGradient(
                            colors = listOf(Color(0xFFFF80AB), Color(0xFF4DB6AC))
                        )
                        Text(
                            text = "Ver carrinho",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontFamily = Poppins,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .background(gradientBrush, RoundedCornerShape(24.dp)) // Aplica o gradiente
                                .padding(horizontal = 16.dp, vertical = 8.dp) // Padding interno do texto
                        )
                    }
                }
            }
        }
    }
}
