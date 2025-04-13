package com.example.warraquizapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.warraquizapp.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository = AuthRepository()) : ViewModel() {
    var currentUser by mutableStateOf(authRepository.currentUser)
        private set

    var errorMessage by mutableStateOf<String?>(null)

    var isUserLoggedIn by mutableStateOf(currentUser != null)
        private set

    init {
        // Listen for authentication state changes
        // Note: Firebase AuthStateListener runs on the main thread.
        FirebaseAuth.getInstance().addAuthStateListener { auth ->
            currentUser = auth.currentUser
            isUserLoggedIn = currentUser != null
        }
    }

    fun signUpWithEmailAndPassword(email: String, password: String) {
        errorMessage = null
        viewModelScope.launch {
            authRepository.signUpWithEmailAndPassword(email, password)
                .onSuccess { user ->
                    currentUser = user
                    isUserLoggedIn = currentUser != null
                }
                .onFailure { e ->
                    errorMessage = e.localizedMessage ?: "Signup failed."
                }
        }
    }

    fun loginWithEmailAndPassword(email: String, password: String) {
        errorMessage = null
        viewModelScope.launch {
            authRepository.loginWithEmailAndPassword(email, password)
                .onSuccess { user ->
                    currentUser = user
                    isUserLoggedIn = currentUser != null
                }
                .onFailure { e ->
                    errorMessage = e.localizedMessage ?: "Login failed."
                }
        }
    }

    fun signOut() {
        authRepository.signOut()
    }
}