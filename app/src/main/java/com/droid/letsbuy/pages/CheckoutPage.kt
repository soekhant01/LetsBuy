package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.letsbuy.AppUtil
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.model.UserModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun CheckoutPage(modifier: Modifier = Modifier) {

    val userModel = remember {
        mutableStateOf(UserModel())
    }

    val productList = remember {
        mutableListOf(ProductModel())
    }

    val subTotal = remember {
        mutableStateOf(0f)
    }

    val total = remember {
        mutableStateOf(0f)
    }

    val discount = remember {
        mutableStateOf(0f)
    }

    val tax = remember {
        mutableStateOf(0f)
    }

    fun calculateTotal() {
        productList.forEach {
            if (it.actualPrice.isNotEmpty()) {
                val quantity = userModel.value.cartItems[it.id] ?: 0
                subTotal.value += it.actualPrice.toFloat() * quantity

            }
        }
        discount.value =
            subTotal.value * (AppUtil.getDiscountPercentage() / 100)
        tax.value = subTotal.value * (AppUtil.getTaxPercentage() / 100)
        total.value = subTotal.value - discount.value + tax.value
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("users")
            .document(FirebaseAuth.getInstance().currentUser?.uid!!).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(UserModel::class.java)
                    if (result != null) {
                        userModel.value = result
                    }
                    Firebase.firestore.collection("data").document("stock")
                        .collection("products")
                        .whereIn("id", userModel.value.cartItems.keys.toList())
                        .get().addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val resultProducts = task.result.toObjects(
                                    ProductModel::class.java
                                )
                                productList.addAll(resultProducts)
                                calculateTotal()

                            }

                        }
                }
            }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text("Checkout", fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Deliver to : ", fontWeight = FontWeight.SemiBold)
            Text(userModel.value.address)
        }
        Spacer(Modifier.height(16.dp))


        HorizontalDivider()
        Spacer(Modifier.height(16.dp))
        RowCheckout("Subtotal", subTotal.value)
        Spacer(Modifier.height(8.dp))
        RowCheckout("Discount", discount.value)
        Spacer(Modifier.height(8.dp))
        RowCheckout("Tax", tax.value)
        Spacer(Modifier.height(16.dp))
        HorizontalDivider()
        Spacer(Modifier.height(16.dp))

        RowCheckout("Total", total.value)


    }
}

@Composable
fun RowCheckout(title: String, value: Float) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(title, fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
        Text("$ ${String.format("%.2f", value)}", fontSize = 20.sp)
    }

}