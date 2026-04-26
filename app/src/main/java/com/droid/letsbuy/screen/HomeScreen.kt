package com.droid.letsbuy.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.droid.letsbuy.pages.CartPage
import com.droid.letsbuy.pages.FavoritePage
import com.droid.letsbuy.pages.HomePage
import com.droid.letsbuy.pages.ProfilePage

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavController) {
    val navItemList = listOf<NavItem>(
        NavItem(
            label = "Home",
            icon = Icons.Default.Home
        ),
        NavItem(
            label = "Favorite",
            icon = Icons.Default.Favorite
        ),
        NavItem(
            label = "Cart",
            icon = Icons.Default.ShoppingCart
        ),
        NavItem(
            label = "Profile",
            icon = Icons.Default.Person
        )
    )

    var selectedIndex by remember {
        mutableIntStateOf(0)
    }

    Scaffold(
        bottomBar = {
            NavigationBar {
                navItemList.forEachIndexed { index, navItem ->
                    NavigationBarItem(
                        selected = index == selectedIndex,
                        onClick = {
                            selectedIndex = index
                        },
                        icon = {
                            Icon(
                                navItem.icon,
                                contentDescription = navItem.label
                            )
                        },
                        label = { Text(navItem.label) },
                    )
                }

            }
        }
    ) {
        ContentScreen(modifier = modifier.padding(it), selectedIndex)
    }

}

@Composable
fun ContentScreen(modifier: Modifier = Modifier, selectedIndex: Int) {

    when (selectedIndex) {
        0 -> HomePage(modifier)
        1 -> FavoritePage(modifier)
        2 -> CartPage(modifier)
        3 -> ProfilePage()
    }

}

data class NavItem(
    val label: String,
    val icon: ImageVector
)