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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.ProductItemView
import com.droid.letsbuy.components.shimmerEffect
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.viewmodel.FavoritePageViewModel
import kotlin.collections.chunked
import kotlin.collections.forEach

@Composable
fun FavoritePage(
    modifier: Modifier = Modifier,
    favoritePageViewModel: FavoritePageViewModel = viewModel()
) {


    val favoriteUiState by favoritePageViewModel.favoriteUiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        favoritePageViewModel.loadFavoriteList(context)
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
        Spacer(Modifier.height(8.dp))

        when {
            favoriteUiState.isLoading -> ShimmerList()
            favoriteUiState.productList.isNotEmpty() -> FavoriteList(productList = favoriteUiState.productList)

            else -> EmptyState()
        }


    }
}


@Composable
fun ShimmerList() {
    LazyColumn {
        items(3) {

            Row {
                repeat(2) {
                    Card(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .height(250.dp)
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
}

@Composable
fun FavoriteList(productList: List<ProductModel>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(productList.chunked(2)) { rowItems ->

            Row {

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
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
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