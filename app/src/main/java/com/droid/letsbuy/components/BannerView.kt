package com.droid.letsbuy.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.droid.letsbuy.viewmodel.HomeUiState
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun BannerView(homeUiState: HomeUiState) {


    val pagerState = rememberPagerState(0) {
        homeUiState.banners.size
    }

    if (homeUiState.isLoading) {
        Column(
            modifier = Modifier
                .height(200.dp)

        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .shimmerEffect()
            )
        }

        Spacer(Modifier.height(16.dp))

    } else {

        Column(
            modifier = Modifier
                .height(230.dp)

        ) {

            HorizontalPager(
                state = pagerState,
                pageSpacing = 24.dp,
                modifier = Modifier.height(200.dp)
            ) {
                AsyncImage(
                    model = homeUiState.banners[it],
                    contentDescription = "Banner image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(Modifier.height(16.dp))
            com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator(
                dotCount = homeUiState.banners.size,
                type = ShiftIndicatorType(
                    DotGraphic(
                        color = MaterialTheme.colorScheme.primary,
                        size = 6.dp
                    )
                ),
                pagerState = pagerState
            )
        }

    }


}