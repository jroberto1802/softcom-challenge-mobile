package com.example.softcomchallengejunior.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softcomchallengejunior.data.remote.dto.CategoryDto
import com.example.softcomchallengejunior.data.remote.dto.ProductDto
import com.example.softcomchallengejunior.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
    val categories = _categories.asStateFlow()
    val selectedCategoryId = _selectedCategoryId.asStateFlow()

    val filteredProducts = combine(
        _products,
        _selectedCategoryId,
        _searchQuery
    ) { products, selectedId, query ->
        products.filter { product ->
            val matchesCategory = selectedId == null || product.categoryId == selectedId
            val matchesQuery = product.name.contains(query, ignoreCase = true)
            matchesCategory && matchesQuery
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            try {
                // Busca categorias e produtos do Repositório (Supabase)
                _categories.value = repository.getCategories()
                _products.value = repository.getProducts()
            } catch (e: Exception) {
                // Aqui podes tratar erros (ex: Log.e ou uma mensagem na UI)
            }
        }
    }

    // 3. Função para selecionar categoria (chamada pelo clique no card)
    fun selectCategory(categoryId: Long) {
        _selectedCategoryId.value = if (_selectedCategoryId.value == categoryId) {
            null // Se clicar na mesma categoria, desmarca (mostra todos)
        } else {
            categoryId // Filtra pela nova categoria
        }
    }
}