package com.droid.letsbuy.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.droid.letsbuy.components.BannerView
import com.droid.letsbuy.components.CategoriesView
import com.droid.letsbuy.components.HeaderView
import com.droid.letsbuy.components.LatestItemView
import com.droid.letsbuy.components.SearchScreen
import com.droid.letsbuy.viewmodel.HomePageViewModel

@Composable
fun HomePage(
    modifier: Modifier,
    viewModel: HomePageViewModel = viewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsState()
    var isSearchActive by rememberSaveable { mutableStateOf(false) }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    val scrollState = rememberScrollState()

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState)
        ) {


            HeaderView(
                homeUiState = homeUiState,
//                onSearchClick = { isSearchActive = true }
            )



            Spacer(Modifier.height(16.dp))


            BannerView(homeUiState)

            CategoriesView(homeUiState)
            Spacer(Modifier.height(16.dp))
            LatestItemView(homeUiState)

        }

        AnimatedVisibility(
            visible = isSearchActive,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
        ) {
            SearchScreen(
                searchQuery = searchQuery,
                onQueryChange = { searchQuery = it },
                onDismiss = {
                    isSearchActive = false
                    searchQuery = ""
                }
            )
        }


    }


}