package com.example.softcomchallengejunior

import CategoryItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.softcomchallengejunior.ui.components.ProductCard
import com.example.softcomchallengejunior.ui.home.HomeViewModel
import com.example.softcomchallengejunior.ui.theme.Poppins
import com.example.softcomchallengejunior.ui.theme.SoftcomChallengeJuniorTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: HomeViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SoftcomChallengeJuniorTheme {
                val categories by viewModel.categories.collectAsState()
                val products by viewModel.filteredProducts.collectAsState()
                val selectedId by viewModel.selectedCategoryId.collectAsState()
                val searchQuery by viewModel.searchQuery.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(
                                            Color(0xFF4ECDC4).copy(alpha = 0.05f),
                                            Color(0xFFFF6B9D).copy(alpha = 0.05f)
                                        )
                                    )
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 32.dp)
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.logo_pet_friends),
                                    contentDescription = "Logo Pet Friends",
                                    modifier = Modifier
                                        .height(50.dp)
                                        .fillMaxWidth(),
                                    contentScale = ContentScale.Fit
                                )
                                Text(
                                    text = "Tudo para seu melhor amigo",
                                    fontSize = 14.sp,
                                    fontFamily = Poppins,
                                    fontWeight = FontWeight.Medium,
                                    lineHeight = 16.sp,
                                    color = Color(0xFF6B7280),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.White)
                    ) {
                        item {
                            OutlinedTextField(
                                value = searchQuery,
                                onValueChange = { viewModel.onSearchQueryChanged(it) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 16.dp, bottom = 24.dp, start = 16.dp, end = 16.dp),
                                placeholder = { Text("O que você procura?") },
                                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                                shape = RoundedCornerShape(25.dp),
                                singleLine = true,
                                colors = TextFieldDefaults.outlinedTextFieldColors(
                                    containerColor = Color(0xFFF9FAFB),
                                    unfocusedBorderColor = Color.Transparent,
                                    focusedBorderColor = Color(0xFFE5E7EB)
                                )
                            )
                        }

                        item {
                            // Carrossel de Categorias (Filtro)
                            LazyRow(
                                modifier = Modifier.fillMaxWidth(),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                items(categories) { category ->
                                    CategoryItem(
                                        category = category,
                                        isSelected = category.id == selectedId,
                                        onClick = { viewModel.selectCategory(category.id) }
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                        }

                        // Seções por Categoria
                        val categoriesToDisplay = if (selectedId != null) {
                            categories.filter { it.id == selectedId }
                        } else {
                            categories
                        }

                        items(categoriesToDisplay) { category ->
                            val categoryProducts = products.filter { it.categoryId == category.id }
                            
                            if (categoryProducts.isNotEmpty()) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(bottom = 24.dp)
                                ) {
                                    Text(
                                        text = category.name,
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        fontFamily = Poppins,
                                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                                        color = Color(0xFF2D3748)

                                    )
                                    
                                    LazyRow(
                                        modifier = Modifier.fillMaxWidth(),
                                        contentPadding = PaddingValues(horizontal = 16.dp),
                                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        items(categoryProducts) { product ->
                                            Box(modifier = Modifier.width(160.dp)) {
                                                ProductCard(
                                                    product = product,
                                                    onClick = { /* Abrir Modal */ }
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
