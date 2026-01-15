package com.example.softcomchallengejunior.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.softcomchallengejunior.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(viewModel: ProductDetailViewModel, onBackClick: () -> Unit) {
    val product = viewModel.product ?: return
    val quantity by viewModel.quantity.collectAsState()
    val observation by viewModel.observation.collectAsState()

    val currentPrice =
        if (product.isPromotion && product.promotionPrice != null) product.promotionPrice else product.price
    val totalPrice = currentPrice * quantity

    LaunchedEffect(Unit) {
        viewModel.productAddedToCartEvent.collect {
            onBackClick()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Detalhes",
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Poppins,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.padding(8.dp).background(Color(0xFFF3F4F6), CircleShape)
                    ) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.White)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(16.dp).navigationBarsPadding()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Quantidade",
                            fontWeight = FontWeight.Medium,
                            fontFamily = Poppins,
                            color = Color(0xFF4B5563)
                        )

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.background(
                                Color(0xFFF9FAFB),
                                RoundedCornerShape(24.dp)
                            ).padding(4.dp)
                        ) {
                            IconButton(
                                onClick = { viewModel.decrementQuantity() },
                                modifier = Modifier.background(
                                    Color.White,
                                    RoundedCornerShape(24.dp)
                                )
                            ) {
                                Text("-", fontSize = 24.sp, color = Color.Gray)
                            }
                            Text(
                                text = quantity.toString(),
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            IconButton(
                                onClick = { viewModel.incrementQuantity() },
                                modifier = Modifier.background(
                                    Color.White,
                                    RoundedCornerShape(24.dp)
                                )
                            ) {
                                Text("+", fontSize = 20.sp, color = Color.Gray)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            viewModel.addItemToCart()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(Color(0xFFFF80AB), Color(0xFF4DB6AC))
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    "Adicionar ao Carrinho",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                                Text(
                                    "R$ ${String.format("%.2f", totalPrice)}",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .background(Color.White)
        ) {
            Box(modifier = Modifier.fillMaxWidth().height(320.dp)) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )

                if (product.isPromotion && product.promotionPrice != null) {
                    val discount =
                        (((product.price - product.promotionPrice) / product.price) * 100).toInt()
                    Surface(
                        modifier = Modifier.padding(16.dp).align(Alignment.TopEnd),
                        color = Color(0xFFEF4444),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text(
                            text = "$discount% OFF",
                            color = Color.White,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = product.name,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        modifier = Modifier.weight(1f)
                    )

                    Column(horizontalAlignment = Alignment.End) {
                        if (product.isPromotion && product.promotionPrice != null) {
                            Text(
                                text = "R$ ${String.format("%.2f", product.price)}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray
                                )
                            )
                            Text(
                                text = "R$ ${String.format("%.2f", product.promotionPrice)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFFF6B9D)
                            )
                        } else {
                            Text(
                                text = "R$ ${String.format("%.2f", product.price)}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Descrição",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = product.description ?: "Sem descrição disponível.",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    "Observações",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp,
                    fontFamily = Poppins
                )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(
                    value = observation,
                    onValueChange = { viewModel.updateObservation(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = {
                        Text(
                            "Alguma observação especial? (opcional)",
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        containerColor = Color(0xFFF9FAFB),
                        unfocusedBorderColor = Color(0xFFE5E7EB)
                    ),
                    supportingText = {
                        Text(
                            text = "${observation.length}/100",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.End
                        )
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}
