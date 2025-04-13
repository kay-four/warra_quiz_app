// ui/navigation/NavGraph.kt
package com.example.warraquizapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.warraquizapp.ui.auth.LoginScreen
import com.example.warraquizapp.ui.auth.SignupScreen
import com.example.warraquizapp.ui.screens.WelcomeScreen

@Composable
fun SetupNavGraph(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "login", // Sets your initial screen
        modifier = modifier
    ) {
        composable("login") {
            LoginScreen(navController = navController)
        }
        composable("signup") {
            SignupScreen(navController = navController)
        }
        composable("welcome") {
            WelcomeScreen(navController = navController)
        }
        // Add other navigation routes for your quiz features
    }
}