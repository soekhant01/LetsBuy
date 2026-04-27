package com.droid.letsbuy

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.droid.letsbuy.pages.CategoryProductsPage
import com.droid.letsbuy.screen.AuthScreen
import com.droid.letsbuy.screen.HomeScreen
import com.droid.letsbuy.screen.LoginScreen
import com.droid.letsbuy.screen.SignupScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {

    val navController = rememberNavController()
    GlobalNavigation.navController = navController
    val isLoggedIn = Firebase.auth.currentUser != null
    val firstScreen = if (isLoggedIn) "home" else "auth"

    NavHost(
        navController = navController,
        startDestination = firstScreen
    ) {
        composable("auth") {
            AuthScreen(modifier, navController)
        }
        composable("login") {
            LoginScreen(modifier, navController = navController)
        }
        composable("signup") {
            SignupScreen(modifier, navController = navController)
        }
        composable("home") {
            HomeScreen(modifier, navController)
        }


        composable("category-products/{categoryId}") {
            val categoryId = it.arguments?.getString("categoryId")
            CategoryProductsPage(modifier, categoryId)
        }
    }
}

object GlobalNavigation {
    lateinit var navController: NavHostController
}