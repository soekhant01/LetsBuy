package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.ProductItemView
import com.droid.letsbuy.components.TopBar
import com.droid.letsbuy.components.shimmerEffect
import com.droid.letsbuy.viewmodel.CategoryProductsViewModel

@Composable
fun CategoryProductsPage(
    modifier: Modifier = Modifier,
    categoryId: String,
    categoryProductsViewModel: CategoryProductsViewModel = viewModel()
) {

    val categoryProductsUiState by categoryProductsViewModel.categoryProductsState.collectAsState()


    LaunchedEffect(Unit) {
        categoryProductsViewModel.loadProducts(categoryId)
    }

    Scaffold(
        topBar = {
            TopBar(
                title = "Products"
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(
                    vertical = paddingValues.calculateTopPadding() - 48.dp,
                    horizontal = 8.dp
                )
        ) {
            if (categoryProductsUiState.isLoading) {

                items(3) { // 3 rows x 2 cards = 6 shimmer placeholders
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        repeat(2) {
                            Card(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(240.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .shimmerEffect(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color.Transparent
                                )
                            ) {}
                        }
                    }
                }

            } else {

                items(categoryProductsUiState.productList.chunked(2)) { rowItems ->
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
    }


}