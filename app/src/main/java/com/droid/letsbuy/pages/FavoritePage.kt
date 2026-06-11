package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.letsbuy.utils.AppUtil
import com.droid.letsbuy.components.CartItemView
import com.droid.letsbuy.components.ProductItemView
import com.droid.letsbuy.model.ProductModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

@Composable
fun FavoritePage(modifier: Modifier = Modifier) {

    val productList = remember {
        mutableStateOf<List<ProductModel>>(emptyList())
    }

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        val favoriteList = AppUtil.getFavoriteList(context)
        if (favoriteList.isNotEmpty()) {
            Firebase.firestore.collection("data").document("stock")
                .collection("products")
                .whereIn("id", favoriteList.toList())
                .get().addOnCompleteListener() {
                    if (it.isSuccessful) {
                        val resultList = it.result.documents.mapNotNull { doc ->
                            doc.toObject(ProductModel::class.java)
                        }
                        productList.value = resultList
                    }

                }
        } else {
            productList.value = emptyList()
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),

        ) {
        Text(
            "Your Favorites", style = TextStyle(
                fontSize = 22.sp, fontWeight = FontWeight.Bold
            )
        )
        if (productList.value.isNotEmpty()) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
            ) {
                items(productList.value.chunked(2)) { rowItems ->

                    Row() {
                        rowItems.forEach { item ->
                            ProductItemView(
                                modifier = Modifier.weight(1f),
                                item
                            )
                        }
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }


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
                    "No Favorite Items Here", style = TextStyle(
                        fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                )
            }
        }


    }
}