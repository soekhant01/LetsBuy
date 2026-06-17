package com.droid.letsbuy.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.droid.letsbuy.model.ProductModel
import com.droid.letsbuy.utils.AppUtil
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.tbuonomo.viewpagerdotsindicator.compose.DotsIndicator
import com.tbuonomo.viewpagerdotsindicator.compose.model.DotGraphic
import com.tbuonomo.viewpagerdotsindicator.compose.type.ShiftIndicatorType

@Composable
fun ProductDetailsPage(modifier: Modifier = Modifier, productId: String) {

    var product by remember {
        mutableStateOf(ProductModel())
    }
    val context = LocalContext.current

    val isFavorite = remember {
        mutableStateOf(AppUtil.checkFavorite(context, productId))
    }

    LaunchedEffect(Unit) {
        Firebase.firestore.collection("data").document("stock")
            .collection("products").document(productId).get()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val result = it.result.toObject(ProductModel::class.java)
                    if (result != null) {
                        product = result
                    }
                }
            }
    }

    Scaffold(
        topBar = {
            TopBar(

                title = "Product Detail"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding() - 16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
//        title
            Text(
                product.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.height(8.dp))
//        horizontal image view
            Column {
                val pagerState = rememberPagerState(0) {
                    product.images.size
                }
                HorizontalPager(
                    state = pagerState,
                    pageSpacing = 24.dp,
                    modifier = Modifier.height(200.dp)
                ) {
                    AsyncImage(
                        model = product.images[it],
                        contentDescription = "Banner image",
                        modifier = Modifier
                            .height(220.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    )
                }
                Spacer(Modifier.height(8.dp))
                DotsIndicator(
                    dotCount = product.images.size,
                    type = ShiftIndicatorType(
                        DotGraphic(
                            color = MaterialTheme.colorScheme.primary,
                            size = 6.dp
                        )
                    ),
                    pagerState = pagerState
                )
            }
            Spacer(Modifier.height(8.dp))

//      price and favorite button

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "$${product.price}",
                    fontSize = 16.sp,
                    style = TextStyle(textDecoration = TextDecoration.LineThrough)
                )
                Spacer(Modifier.width(9.dp))
                Text(
                    text = "$${product.actualPrice}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,

                    )
                Spacer(Modifier.weight(1f))

                IconButton(onClick = {
                    AppUtil.addOrRemoveFromFavorite(context, productId)
                    isFavorite.value = AppUtil.checkFavorite(context, productId)
                }) {
                    Icon(
                        imageVector = if (isFavorite.value) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        tint = if (isFavorite.value) Color.Red else Color.Gray,
                        contentDescription = "Add to favorite"
                    )
                }
            }


//      add to  cart button
            Button(
                onClick = {
                    AppUtil.addToCart(productId, context)
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp)
            ) {
                Text("Add to Cart", fontSize = 16.sp)
            }
            Spacer(Modifier.height(16.dp))

//        description
            Text(
                "Product Description:",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Spacer(Modifier.height(8.dp))
            Text(
                product.description,
                fontSize = 16.sp,
                color = Color.Gray,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(horizontal = 16.dp)

            )
            Spacer(Modifier.height(16.dp))


//        other details
            if (product.otherDetails.isNotEmpty()) {
                Text(
                    "Product Details: ",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(Modifier.height(8.dp))

            }

            product.otherDetails.forEach { (key, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 26.dp, vertical = 8.dp)
                ) {
                    Text(
                        "$key : ",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,

                        )
                    Text(value, fontSize = 16.sp, color = Color.Gray)
                }
            }

        }
    }


}