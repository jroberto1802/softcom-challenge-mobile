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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
                // Observando os estados do ViewModel
                val categories by viewModel.categories.collectAsState()
                val products by viewModel.filteredProducts.collectAsState()
                val selectedId by viewModel.selectedCategoryId.collectAsState()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .statusBarsPadding()
                                .padding(top = 36.dp)
                                .padding(bottom = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_pet_friends),
                                contentDescription = "Logo Pet Friends",
                                modifier = Modifier.height(50.dp).fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    }
                ) { innerPadding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                            .background(Color.White)
                    ) {
                        val searchQuery by viewModel.searchQuery.collectAsState()

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
                                .padding(horizontal = 16.dp, vertical = 4.dp)
                                .align(Alignment.CenterHorizontally) // Garante o alinhamento na Column
                        )

                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { viewModel.onSearchQueryChanged(it) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            placeholder = { Text("O que você procura?") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                            shape = RoundedCornerShape(25.dp),
                            singleLine = true,
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0xFFF5F5F5),
                                unfocusedBorderColor = Color.Transparent,
                                focusedBorderColor = Color.LightGray
                            )
                        )
                        // 2. CARROSSEL DE CATEGORIAS
                        LazyRow(
                            modifier = Modifier.fillMaxWidth(),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
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

                        // 3. GRADE DE PRODUTOS (2 colunas)
                        // A lista 'products' aqui já é a filtrada pelo ViewModel
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(products) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = {
                                        /* Próximo passo: Abrir Modal de Detalhes */
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}