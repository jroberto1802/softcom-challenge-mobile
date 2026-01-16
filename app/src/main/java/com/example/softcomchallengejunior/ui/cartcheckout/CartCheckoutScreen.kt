package com.example.softcomchallengejunior.ui.cartcheckout

import android.widget.Toast
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.softcomchallengejunior.ui.theme.Poppins

@Composable
fun CartCheckoutScreen(
    totalPaid: Double,
    viewModel: CartCheckoutViewModel = hiltViewModel(),
    onNewOrderClick: () -> Unit
) {
    val orderNumber = remember { viewModel.generateOrderNumber() }
    val totalPaid = totalPaid
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .systemBarsPadding()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        // Ícone de checkmark com gradiente
        Surface(
            modifier = Modifier.size(100.dp),
            shape = CircleShape,
            color = Color.Transparent,
            contentColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFFFF80AB),
                                Color(0xFF4DB6AC)
                            )
                        ),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Pedido Confirmado",
                    modifier = Modifier.size(50.dp),
                    tint = Color.White
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Pedido Finalizado!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = Poppins,
            color = Color(0xFF1F2937)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Seu pedido foi realizado com sucesso e será\nprocessado em breve.",
            fontSize = 16.sp,
            fontFamily = Poppins,
            color = Color(0xFF6B7280),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Card de detalhes do pedido
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            color = Color(0xFFF9FAFB),
            shadowElevation = 4.dp
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Número do pedido", fontFamily = Poppins, fontSize = 14.sp, color = Color(0xFF4B5563))
                    Text(
                        text = orderNumber,
                        fontFamily = Poppins,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B9D)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Total pago", fontFamily = Poppins, fontSize = 14.sp, color = Color(0xFF4B5563))
                    Text(
                        text = "R$ ${String.format("%.2f", totalPaid)}",
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFFF6B9D)
                    )
                        }
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

            // Botão "Realizar Novo Pedido"
            Button(
                onClick = onNewOrderClick,
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
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Icon(Icons.Default.Add, contentDescription = "", tint = Color.White, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Realizar Novo Pedido",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontFamily = Poppins
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.navigationBarsPadding()) // Garante padding para gesture nav
        }
    }