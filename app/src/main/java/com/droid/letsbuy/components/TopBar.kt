package com.droid.letsbuy.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.droid.letsbuy.GlobalNavigation

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String) {

    TopAppBar(
        title = { Text(title, fontWeight = FontWeight.SemiBold) },

        navigationIcon = {
            IconButton(onClick = {
                GlobalNavigation.navController.popBackStack()
            }) {
                Icon(Icons.Filled.ArrowBackIosNew, "Back")
            }
        },
        

        )
}