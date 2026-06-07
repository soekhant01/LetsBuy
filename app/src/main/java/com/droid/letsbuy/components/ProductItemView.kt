package com.droid.letsbuy.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.droid.letsbuy.AppUtil
import com.droid.letsbuy.GlobalNavigation
import com.droid.letsbuy.model.ProductModel

@Composable
fun ProductItemView(modifier: Modifier = Modifier, product: ProductModel) {

    val context = LocalContext.current
    Card(
        onClick = {
            GlobalNavigation.navController.navigate("product-details/${product.id}")
        },
        modifier = modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(4.dp)

    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            AsyncImage(
                model = product.images.firstOrNull(),
                contentDescription = product.category,
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
            )
            Text(
                product.title,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(8.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$${product.price}",
                    fontSize = 14.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(Modifier.width(9.dp))
                Text(
                    text = "$${product.actualPrice}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,

                    )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    AppUtil.addToCart(product.id, context)
                }) {
                    Icon(
                        Icons.Default.ShoppingCart,
                        contentDescription = "Add to cart"
                    )
                }
            }
        }
    }
}
