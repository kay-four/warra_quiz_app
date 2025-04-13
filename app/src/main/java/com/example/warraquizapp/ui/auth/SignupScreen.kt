// ui/auth/SignupScreen.kt
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
fun SignupScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = viewModel()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Create Account", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))
        InputField(value = remember { mutableStateOf(email) }, label = "Email")
        InputField(value = remember { mutableStateOf(password) }, label = "Password")
        InputField(value = remember { mutableStateOf(confirmPassword) }, label = "Confirm Password")
        PrimaryButton(
            onClick = {
                if (password == confirmPassword) {
                    authViewModel.signUpWithEmailAndPassword(email, password)
                } else {
                    authViewModel.errorMessage = "Passwords do not match."
                }
            },
            text = "Sign Up",
            enabled = email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()
        )
        ErrorText(errorMessage = authViewModel.errorMessage)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text("Already have an account?")
            TextButton(onClick = { navController.navigate("login") }) {
                Text("Login")
            }
        }

        LaunchedEffect(authViewModel.isUserLoggedIn) {
            if (authViewModel.isUserLoggedIn) {
                navController.navigate("welcome") {
                    popUpTo("signup") { inclusive = true }
                }
            }
        }
    }
}