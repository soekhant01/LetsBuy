package com.droid.letsbuy.viewmodel

import androidx.lifecycle.ViewModel
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.model.UserModel
import com.droid.letsbuy.utils.AppUtil
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CheckOutViewModel : ViewModel() {
    private val _userModel = MutableStateFlow(UserModel())
    val userModel: StateFlow<UserModel> = _userModel.asStateFlow()

    private val _productList = MutableStateFlow<List<ProductModel>>(emptyList())

    private val _subTotal = MutableStateFlow(0f)
    val subTotal: StateFlow<Float> = _subTotal.asStateFlow()

    private val _total = MutableStateFlow(0f)
    val total: StateFlow<Float> = _total.asStateFlow()

    private val _discount = MutableStateFlow(0f)
    val discount: StateFlow<Float> = _discount.asStateFlow()
    private val _tax = MutableStateFlow(0f)
    val tax: StateFlow<Float> = _tax.asStateFlow()

    init {
        loadCheckout()
    }

    private fun loadCheckout() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        Firebase.firestore.collection("users")
            .document(uid)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val result = task.result.toObject(UserModel::class.java)
                    if (result != null) {
                        _userModel.value = result
                        loadProducts(result)
                    }
                }
            }
    }

    private fun loadProducts(user: UserModel) {
        val cartKeys = user.cartItems.keys.toList()
        if (cartKeys.isEmpty()) return

        Firebase.firestore.collection("data").document("stock")
            .collection("products").whereIn("id", cartKeys).get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val resultProducts =
                        task.result.toObjects(ProductModel::class.java)
                    _productList.value = resultProducts
                    calculateTotal(user, resultProducts)
                }
            }

    }


    private fun calculateTotal(user: UserModel, products: List<ProductModel>) {
        var subTotal = 0f
        products.forEach { product ->
            if (product.actualPrice.isNotEmpty()) {
                val quantity = user.cartItems[product.id] ?: 0
                subTotal += product.actualPrice.toFloat() * quantity
            }

        }

        val discount = subTotal * (AppUtil.getDiscountPercentage() / 100)
        val tax = subTotal * (AppUtil.getTaxPercentage() / 100)
        val total = subTotal - (discount + tax)

        _subTotal.value = subTotal
        _discount.value = discount
        _tax.value = tax
        _total.value = total
    }
}