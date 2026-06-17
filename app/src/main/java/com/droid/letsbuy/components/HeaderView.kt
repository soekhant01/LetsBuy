package com.droid.letsbuy.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.droid.letsbuy.viewmodel.HomeUiState

//@Composable
//fun HeaderView(
//    homeUiState: HomeUiState,
//) {
//
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        if (homeUiState.isLoading) {
//            Column(
//                modifier = Modifier.weight(1f)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(0.4f)
//                        .clip(RoundedCornerShape(4.dp))
//                        .height(16.dp)
//                        .shimmerEffect()
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth(0.3f)
//                        .clip(RoundedCornerShape(4.dp))
//                        .height(16.dp)
//                        .shimmerEffect()
//                )
//
//            }
//        } else {
//            Column {
//                Text(
//                    "Welcome Back!",
//                    style = TextStyle(
//                        fontFamily = FontFamily.Monospace,
//                        fontSize = 18.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//                Spacer(Modifier.height(8.dp))
//                Text(
//                    homeUiState.userName,
//                    style = TextStyle(
//                        fontFamily = FontFamily.Monospace,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Bold
//                    )
//                )
//            }
//
//        }
//
//        IconButton(onClick = {
//
//        }) {
//            Icon(
//                imageVector = Icons.Default.Search,
//                contentDescription = "Search"
//            )
//        }
//    }
//
//}


@Composable
fun HeaderView(
    homeUiState: HomeUiState,
    onSearchClick: () -> Unit
) {
    NormalHeaderView(
        homeUiState = homeUiState,
        onSearchClick = onSearchClick
    )
}

@Composable
private fun NormalHeaderView(
    homeUiState: HomeUiState,
    onSearchClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if (homeUiState.isLoading) {
            Column(modifier = Modifier.weight(1f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .clip(RoundedCornerShape(4.dp))
                        .height(16.dp)
                        .shimmerEffect()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.3f)
                        .clip(RoundedCornerShape(4.dp))
                        .height(16.dp)
                        .shimmerEffect()
                )
            }
        } else {
            Column {
                Text(
                    "Welcome Back!",
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    homeUiState.userName,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        IconButton(onClick = onSearchClick) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search"
            )
        }
    }
}

