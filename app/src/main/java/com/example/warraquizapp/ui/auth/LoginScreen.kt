// ui/auth/LoginScreen.kt
package com.example.warraquizapp.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.warraquizapp.ui.components.ErrorText
import com.example.warraquizapp.ui.components.InputField
import com.example.warraquizapp.ui.components.PrimaryButton
import com.example.warraquizapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome Back!", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        InputField(value = remember { mutableStateOf(email) }, label = "Email")
        InputField(value = remember { mutableStateOf(password) }, label = "Password")
        PrimaryButton(
            onClick = {
                authViewModel.loginWithEmailAndPassword(email, password)
            },
            text = "Login",
            enabled = email.isNotEmpty() && password.isNotEmpty()
        )
        ErrorText(errorMessage = authViewModel.errorMessage)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Don't have an account?")
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Sign Up")
            }
        }

        LaunchedEffect(authViewModel.isUserLoggedIn) {
            if (authViewModel.isUserLoggedIn) {
                navController.navigate("welcome") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}

