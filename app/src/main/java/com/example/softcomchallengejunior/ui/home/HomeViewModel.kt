package com.example.softcomchallengejunior.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.softcomchallengejunior.data.remote.dto.CategoryDto
import com.example.softcomchallengejunior.data.remote.dto.ProductDto
import com.example.softcomchallengejunior.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

sealed interface HomeUiEvent {
    data class NavigateToDetails(val productJson: String) : HomeUiEvent
}

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryDto>>(emptyList())
    private val _products = MutableStateFlow<List<ProductDto>>(emptyList())
    private val _selectedCategoryId = MutableStateFlow<Long?>(null)
    private val _searchQuery = MutableStateFlow("")
    private val _uiEvent = Channel<HomeUiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()
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

    fun onProductSelected(product: ProductDto) {
        viewModelScope.launch {
            val productJson = Json.encodeToString(product)
            _uiEvent.send(HomeUiEvent.NavigateToDetails(productJson))
        }
    }

    init {
        fetchHomeData()
    }

    private fun fetchHomeData() {
        viewModelScope.launch {
            try {
                _categories.value = repository.getCategories()
                _products.value = repository.getProducts()
            } catch (e: Exception) {
            }
        }
    }

    fun selectCategory(categoryId: Long) {
        _selectedCategoryId.value = if (_selectedCategoryId.value == categoryId) {
            null
        } else {
            categoryId
        }
    }
}
