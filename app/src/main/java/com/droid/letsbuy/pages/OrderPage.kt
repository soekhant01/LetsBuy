package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OrderPage(modifier: Modifier = Modifier) {


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
    }
}