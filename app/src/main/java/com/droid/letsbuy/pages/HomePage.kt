package com.droid.letsbuy.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.BannerView
import com.droid.letsbuy.components.CategoriesView
import com.droid.letsbuy.components.HeaderView
import com.droid.letsbuy.viewmodel.HomePageViewModel

@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    viewModel: HomePageViewModel = viewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .statusBarsPadding(),
    ) {
        HeaderView(homeUiState)

        Spacer(Modifier.height(16.dp))

        BannerView(homeUiState)

        CategoriesView(homeUiState)

    }
}