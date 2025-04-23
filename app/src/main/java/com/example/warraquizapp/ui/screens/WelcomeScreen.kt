// ui/screens/WelcomeScreen.kt
package com.example.warraquizapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.warraquizapp.viewmodel.AuthViewModel

@Composable
fun WelcomeScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    val currentUser = authViewModel.currentUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Welcome to Warra Quiz App!",
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(16.dp))
        currentUser?.email?.let {
            Text(text = "Logged in as: $it")
        } ?: run {
            Text(text = "Not logged in.")
        }
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = { authViewModel.signOut() }) {
            Text("Sign Out")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { /* Navigate to quiz start screen (e.g., navController.navigate("quiz") */ }) {
            Text("Start Quiz")
        }
    }

    // Observe the isUserLoggedIn state and navigate to login when it becomes false
    LaunchedEffect(authViewModel.isUserLoggedIn) {
        if (!authViewModel.isUserLoggedIn) {
            navController.navigate("login") {
                popUpTo("welcome") { inclusive = true }
            }
        }
    }

}