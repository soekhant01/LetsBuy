package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.utils.AppUtil
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class FavoriteUiState(
    val isLoading: Boolean = false,
    val productList: List<ProductModel> = emptyList()
)

class FavoritePageViewModel : ViewModel() {

    private val _favoriteUiState = MutableStateFlow(FavoriteUiState())
    val favoriteUiState = _favoriteUiState.asStateFlow()


    fun loadFavoriteList(context: android.content.Context) {
        viewModelScope.launch {
            _favoriteUiState.value =
                _favoriteUiState.value.copy(isLoading = true)
            try {
                val favoriteList = AppUtil.getFavoriteList(context)
                if (favoriteList.isNotEmpty()) {

                    val result =
                        Firebase.firestore.collection("data").document("stock")
                            .collection("products")
                            .whereIn("id", favoriteList.toList())
                            .get().await()
                    val resultList = result.documents.mapNotNull { doc ->
                        doc.toObject(ProductModel::class.java)
                    }

                    _favoriteUiState.value = FavoriteUiState(
                        isLoading = false,
                        productList = resultList
                    )

                } else {
                    _favoriteUiState.value = FavoriteUiState(
                        isLoading = false,
                        productList = emptyList()
                    )
                }

            } catch (e: Exception) {
                _favoriteUiState.value = FavoriteUiState(
                    isLoading = false,
                    productList = emptyList()
                )
            }


        }
    }

}