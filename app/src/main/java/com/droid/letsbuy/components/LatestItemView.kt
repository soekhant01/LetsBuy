package com.droid.letsbuy.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.letsbuy.viewmodel.HomeUiState

@Composable
fun LatestItemView(homeUiState: HomeUiState) {
    if (homeUiState.isLoading) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.3f)
                    .clip(RoundedCornerShape(4.dp))
                    .height(16.dp)
                    .shimmerEffect()
            )
            Spacer(Modifier.height(16.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(5) {
                    Card(
                        modifier = Modifier
                            .width(240.dp)
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
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Latest Items",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "See all",
                fontSize = 13.sp,
                color = Color(0xFF1D9E75),
                modifier = Modifier.clickable {

                }
            )
        }

        LazyRow(
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(homeUiState.latestItems) { product ->
                ProductItemView(
                    product = product
                )
            }
        }
    }


}