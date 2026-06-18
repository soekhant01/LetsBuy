package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import com.droid.letsbuy.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class CartUiState(
    val isLoading: Boolean = true,
    val user: UserModel = UserModel()
)

class CartViewModel : ViewModel() {

    private val _cartUiState = MutableStateFlow(CartUiState())
    val cartUiState = _cartUiState.asStateFlow()

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

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}