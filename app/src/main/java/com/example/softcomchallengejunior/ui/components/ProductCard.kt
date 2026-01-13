package com.example.softcomchallengejunior.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage // Se este der erro, veja o passo 2 abaixo
import com.example.softcomchallengejunior.data.remote.dto.ProductDto

@Composable
fun ProductCard(
    product: ProductDto,
    onClick: () -> Unit
) {
    // 1. Lógica consolidada: É promoção se a flag for true E o preço promo existir
    val showPromotion = product.isPromotion && product.promotionPrice != null

    // 2. Cálculo do percentual (calculado apenas se necessário)
    val discountPercentage = if (showPromotion) {
        val discount = ((product.price - product.promotionPrice!!) / product.price) * 100
        discount.toInt()
    } else 0

    Card(
        modifier = Modifier
            .fillMaxWidth() // Deixa a Grid controlar a largura
            .padding(4.dp)   // Pequeno respiro entre os cards
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column {
            // 1. CONTAINER DE IMAGEM COM ALTURA FIXA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp) // Altura fixa garante simetria visual
                    .background(Color(0xFFF5F5F5))
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                if (showPromotion) {
                    // Badge de desconto (já implementado)
                }
            }

            Column(modifier = Modifier.padding(12.dp)) {
                // 2. TEXTO COM ALTURA MÍNIMA RESERVADA
                Text(
                    text = product.name,
                    fontSize = 14.sp,
                    maxLines = 2,
                    minLines = 2, // CRUCIAL: Reserva espaço para 2 linhas sempre
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 18.sp
                )

                Spacer(modifier = Modifier.height(8.dp))

                // 3. CONTAINER DE PREÇO COM ALTURA FIXA (Opcional, mas recomendado)
                Column(modifier = Modifier.height(45.dp)) {
                    if (showPromotion) {
                        Text(
                            text = "R$ ${String.format("%.2f", product.price)}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                textDecoration = TextDecoration.LineThrough,
                                color = Color.Gray
                            )
                        )
                        Text(
                            text = "R$ ${String.format("%.2f", product.promotionPrice)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFFF4081)
                        )
                    } else {
                        // Spacer para manter o alinhamento quando não há preço antigo
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "R$ ${String.format("%.2f", product.price)}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}