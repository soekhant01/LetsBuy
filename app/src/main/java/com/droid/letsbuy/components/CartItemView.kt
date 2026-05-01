package com.droid.letsbuy.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CartItemView(
    modifier: Modifier = Modifier,
    productId: String,
    quantity: Long
) {

    Text("$productId ----> $quantity")

}