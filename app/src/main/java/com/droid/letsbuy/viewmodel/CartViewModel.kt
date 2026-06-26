package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartUiState(
    val isLoading: Boolean = true,
    val user: UserModel = UserModel()
)

class CartViewModel : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState())
    val cartUiState = _cartUiState.asStateFlow()

    private val _product = MutableStateFlow(ProductModel())
    val product: StateFlow<ProductModel> = _product.asStateFlow()

    private var listener: ListenerRegistration? = null

    fun startListening() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return
        _cartUiState.value = cartUiState.value.copy(isLoading = true)
        listener = Firebase.firestore.collection("users").document(uid)
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val user = snapshot.toObject(UserModel::class.java)
                    if (user != null) {
                        _cartUiState.value = CartUiState(
                            isLoading = false,
                            user = user
                        )
                    }
                }
            }
    }

    fun loadProduct(productId: String) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products").document(productId).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        _product.value = result
                    }
                }
            }
    }


    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}