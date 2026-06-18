package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.CartItemView
import com.droid.letsbuy.components.shimmerEffect
import com.droid.letsbuy.viewmodel.CartUiState
import com.droid.letsbuy.viewmodel.CartViewModel

@Composable
fun CartPage(
    modifier: Modifier = Modifier,
    cartViewModel: CartViewModel = viewModel()
) {
    val cartUiState by cartViewModel.cartUiState.collectAsState()

    LaunchedEffect(Unit) {
        cartViewModel.startListening()
    }

    when {
        cartUiState.isLoading -> CartShimmerList(modifier = modifier)
        else -> YourCartList(modifier = modifier, cartUiState = cartUiState)

    }


}

@Composable
fun YourCartList(modifier: Modifier = Modifier, cartUiState: CartUiState) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {
        Text(
            "Your Cart", style = TextStyle(
                fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
        )
        Spacer(Modifier.height(8.dp))


        if (cartUiState.user.cartItems.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(
                    cartUiState.user.cartItems.toList(),
                    key = { it.first }) { (productId, quantity) ->
                    CartItemView(productId = productId, quantity = quantity)
                }
            }
        } else {

            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally


            ) {
                Text(
                    "You haven't any cart!", style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                )
            }


        }


//        Button(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(50.dp),
//            onClick = {
//                GlobalNavigation.navController.navigate("checkout")
//            }
//        ) {
//            Text("Checkout")
//        }
    }
}

@Composable
fun CartShimmerList(modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        items(3) {

            Row {

                Card(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .height(180.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .shimmerEffect(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent
                    )
                ) {}


            }
        }
    }
}