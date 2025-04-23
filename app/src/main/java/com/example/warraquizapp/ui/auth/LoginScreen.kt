// ui/auth/LoginScreen.kt
package com.example.warraquizapp.ui.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.warraquizapp.R
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
        // Logo
        Image(
            painter = painterResource(id = R.drawable.warra_logo),
            contentDescription = "Warra Logo",
            modifier = Modifier.size(120.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Welcome Back!", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        InputField(value = email, onValueChange = { email = it }, label = "Email")
        InputField(value = password, onValueChange = { password = it }, label = "Password")

        PrimaryButton(
            onClick = {
                authViewModel.loginWithEmailAndPassword(email, password)
            },
            text = "Login",
            enabled = email.isNotEmpty() && password.isNotEmpty()
        )

        ErrorText(errorMessage = authViewModel.errorMessage)

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Don't have an account?")
            TextButton(onClick = { navController.navigate("signup") }) {
                Text("Sign Up")
            }
        }

        // Navigation to welcome screen after login
        LaunchedEffect(authViewModel.isUserLoggedIn) {
            if (authViewModel.isUserLoggedIn) {
                navController.navigate("welcome") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }
}