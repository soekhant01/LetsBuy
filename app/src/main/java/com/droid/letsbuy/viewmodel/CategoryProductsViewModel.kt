package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.letsbuy.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class CategoryProductsUiState(
    val isLoading: Boolean = false,
    val productList: List<ProductModel> = emptyList(),
    val error: String? = null
)

class CategoryProductsViewModel : ViewModel() {
    private val _categoryProductsState = MutableStateFlow(
        CategoryProductsUiState()
    )

    val categoryProductsState: StateFlow<CategoryProductsUiState> =
        _categoryProductsState.asStateFlow()
    private var loadedCategoryId: String? = null
    fun loadProducts(categoryId: String) {

        // avoid re-fetching if we already have data for this category
        if (categoryId == loadedCategoryId && _categoryProductsState.value.productList.isNotEmpty()) return
        loadedCategoryId = categoryId

        viewModelScope.launch {
            try {
                _categoryProductsState.value =
                    _categoryProductsState.value.copy(isLoading = true)

                val snapshot = Firebase.firestore.collection("data")
                    .document("stock")
                    .collection("products")
                    .whereEqualTo("category", categoryId)
                    .get()
                    .await()

                val resultList = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(ProductModel::class.java)
                }

                _categoryProductsState.value =
                    _categoryProductsState.value.copy(
                        isLoading = false,
                        productList = resultList
                    )
            } catch (e: Exception) {
                _categoryProductsState.value =
                    _categoryProductsState.value.copy(
                        isLoading = false,
                        error = e.message ?: "Something went wrong"
                    )
            }
        }

    }
}