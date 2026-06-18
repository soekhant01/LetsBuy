package com.droid.letsbuy.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ProfileShimmer(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp)
    ) {

        // Avatar shimmer
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )

            Spacer(Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .width(120.dp)
                    .height(16.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmerEffect()
            )

            Spacer(Modifier.height(8.dp))

            Box(
                modifier = Modifier
                    .width(160.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .shimmerEffect()
            )
        }

        Spacer(Modifier.height(28.dp))

        // — Account shimmer card —
        ProfileCardShimmer()

        Spacer(Modifier.height(24.dp))

        // — Preferences shimmer card —
        ProfileCardShimmer()

        Spacer(Modifier.height(24.dp))

        // — Button shimmer —
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clip(RoundedCornerShape(12.dp))
                .shimmerEffect()
        )
    }
}


@Composable
fun ProfileCardShimmer() {

    Card(
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.outlineVariant),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(0.dp)
    ) {

        Column(Modifier.padding(16.dp)) {

            repeat(3) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // icon shimmer
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .shimmerEffect()
                    )

                    Spacer(Modifier.width(12.dp))

                    Column(Modifier.weight(1f)) {

                        Box(
                            modifier = Modifier
                                .width(80.dp)
                                .height(10.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )

                        Spacer(Modifier.height(6.dp))

                        Box(
                            modifier = Modifier
                                .width(140.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .shimmerEffect()
                        )
                    }
                }
            }
        }
    }
}