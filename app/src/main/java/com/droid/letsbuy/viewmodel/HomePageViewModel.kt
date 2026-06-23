package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droid.letsbuy.model.BannerModel
import com.droid.letsbuy.model.CategoryModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


data class HomeUiState(
    val isLoading: Boolean = true,
    val userName: String = "",
    val banners: List<String> = emptyList(),
    val categories: List<CategoryModel> = emptyList()
)

class HomePageViewModel : ViewModel() {
    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        loadHome()
    }

    private fun loadHome() {
        viewModelScope.launch {
            try {


                val uid =
                    FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
//               for header view
                val userTask =
                    Firebase.firestore.collection("users").document(uid)
                        .get().await()
                val name = userTask.getString("name") ?: ""


//                for banner view
                val bannerTask = Firebase.firestore
                    .collection("data")
                    .document("banners")
                    .get()
                    .await().toObject(BannerModel::class.java)


                val banners = bannerTask?.urls ?: emptyList()

//                for categories view
                val categoriesTask =
                    Firebase.firestore.collection("data").document("stock")
                        .collection("categories").get().await()

                val categories = categoriesTask.documents.mapNotNull {
                    it.toObject(CategoryModel::class.java)
                }

                _homeUiState.value = HomeUiState(
                    isLoading = false,
                    userName = name,
                    banners = banners,
                    categories = categories
                )
            } catch (_: Exception) {
                _homeUiState.value = _homeUiState.value.copy(isLoading = false)

            }
        }

    }
}