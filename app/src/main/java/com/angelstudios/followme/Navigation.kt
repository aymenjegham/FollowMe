package com.angelstudios.followme

import MainScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ScreenRoutes.MainScreen.route,
    onComposableReady : ()-> Unit
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = context){
        onComposableReady()
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = ScreenRoutes.MainScreen.route) {
            MainScreen() {

            }
        }
    }
}

sealed class ScreenRoutes(val route: String) {
    object MainScreen : ScreenRoutes("main")
}