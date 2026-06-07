package com.droid.letsbuy.components

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun BannerView() {
    var bannerList by remember {
        mutableStateOf<List<String>>(emptyList())
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("banners").get()
            .addOnCompleteListener {
                if (it.isSuccessful && it.result != null) {
                    val urls = it.result.get("urls") as? List<String>
                    bannerList = urls ?: emptyList()
                } else {
                    Log.e(
                        "BannerView",
                        "Failed to load banners: ${it.exception?.message}"
                    )
                }
            }
    }
    if (bannerList.isEmpty()) return

    Column(
        modifier = Modifier
            .height(230.dp)

    ) {
        val pagerState = rememberPagerState(0) {
            bannerList.size
        }
        HorizontalPager(
            state = pagerState,
            pageSpacing = 24.dp,
            modifier = Modifier.height(200.dp)
        ) {
            AsyncImage(
                model = bannerList[it],
                contentDescription = "Banner image",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
            )
        }
        Spacer(Modifier.height(8.dp))
        com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator(
            dotCount = bannerList.size,
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