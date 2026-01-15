package com.example.softcomchallengejunior.ui.cart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.softcomchallengejunior.data.local.entities.CartItemEntity
import com.example.softcomchallengejunior.ui.theme.Poppins

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
    onCheckoutClick: () -> Unit
) {
    val cartItems by viewModel.cartItems.collectAsState()
    val totalPrice by viewModel.totalPrice.collectAsState()
    val totalItems by viewModel.totalItems.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Meu Carrinho",
                        fontWeight = FontWeight.Bold,
                        fontFamily = Poppins,
                        fontSize = 18.sp
                    )
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color(0xFFF9FAFB), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = "Voltar")
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
                    modifier = Modifier
                        .padding(16.dp)
                        .navigationBarsPadding()
                ) {
                    Button(
                        onClick = onBackClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
                    ) {
                        Text(
                            "+ Adicionar Mais Itens",
                            color = Color(0xFF374151),
                            fontWeight = FontWeight.Bold,
                            fontFamily = Poppins
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = onCheckoutClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(0.dp)
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
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    "Finalizar Pedido ",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = Poppins
                                )
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFFDFDFD)),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(cartItems) { item ->
                CartItemCard(
                    item = item,
                    onIncrement = { viewModel.updateQuantity(item, true) },
                    onDecrement = { viewModel.updateQuantity(item, false) },
                    onRemove = { viewModel.removeItem(item) }
                )
            }

            if (cartItems.isNotEmpty()) {
                item {
                    OrderSummaryCard(totalItems = totalItems, totalPrice = totalPrice)
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItemEntity,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    onRemove: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF9FAFB)),
                contentScale = ContentScale.Fit
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    color = Color(0xFF1F2937)
                )
                Text(
                    text = "R$ ${String.format("%.2f", item.price)}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 14.sp,
                    color = Color(0xFF374151)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .background(Color(0xFFF9FAFB), RoundedCornerShape(24.dp))
                        .padding(4.dp)
                ) {
                    // Botão Menos
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color.White)
                            .clickable { onDecrement() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("-", fontSize = 18.sp, color = Color.Gray, fontWeight = FontWeight.Medium)
                    }

                    Text(
                        text = item.quantity.toString(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 12.dp)
                    )

                    // Botão Mais
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFFF80AB))
                            .clickable { onIncrement() },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("+", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Medium)
                    }
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = "Remover",
                    tint = Color(0xFFFF80AB),
                    modifier = Modifier
                        .size(32.dp)
                        .background(Color(0xFFFEF2F2), CircleShape)
                        .padding(6.dp)
                )
            }
        }
    }
}

@Composable
fun OrderSummaryCard(totalItems: Int, totalPrice: Double) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF9FAFB).copy(alpha = 0.5f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                "Resumo do Pedido",
                fontWeight = FontWeight.Bold,
                fontFamily = Poppins,
                fontSize = 16.sp,
                color = Color(0xFF1F2937)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Subtotal ($totalItems itens)",
                    color = Color(0xFF6B7280),
                    fontFamily = Poppins,
                    fontSize = 14.sp
                )
                Text(
                    "R$ ${String.format("%.2f", totalPrice)}",
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF374151),
                    fontFamily = Poppins,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            HorizontalDivider(color = Color(0xFFE5E7EB))
            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Total",
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 16.sp,
                    color = Color(0xFF1F2937)
                )
                Text(
                    "R$ ${String.format("%.2f", totalPrice)}",
                    fontWeight = FontWeight.Bold,
                    fontFamily = Poppins,
                    fontSize = 18.sp,
                    color = Color(0xFFFF80AB)
                )
            }
        }
    }
}
