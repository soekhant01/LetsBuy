package com.droid.letsbuy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.droid.letsbuy.GlobalNavigation
import com.droid.letsbuy.utils.AppUtil
import com.droid.letsbuy.viewmodel.CartViewModel

@Composable
fun CartItemView(
    productId: String,
    quantity: Long,
    cartViewModel: CartViewModel = viewModel(key = productId)

) {

    val context = LocalContext.current

    val product by cartViewModel.product.collectAsState()

    val openDialog = remember { mutableStateOf(false) }


    LaunchedEffect(productId) {
        cartViewModel.loadProduct(productId)
    }

    Card(
        onClick = {
            GlobalNavigation.navController.navigate("product-details/${product.id}")
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(4.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.category,
                modifier = Modifier.size(100.dp)
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .weight(1f)
            ) {
                Text(
                    product.title,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )

                Text(
                    text = "$${product.actualPrice}",
                    fontSize = 14.sp,
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        AppUtil.removeFromCart(productId, context)
                    }) {
                        Text(
                            "-",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text("$quantity", fontSize = 16.sp)

                    IconButton(onClick = {
                        AppUtil.addToCart(productId, context)
                    }) {
                        Text(
                            "+",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            IconButton(onClick = {
                openDialog.value = true
            }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Icon"
                )
            }
        }
    }

    if (openDialog.value) DeleteConfirmDialog(
        onDismissRequest = { openDialog.value = false },
        onConfirmation = {
            openDialog.value = false

            AppUtil.removeFromCart(productId, context, true)
        },
        dialogText = "Are You Sure To Delete?",
        dialogTitle = "Delete Confirmation"
    )


}


