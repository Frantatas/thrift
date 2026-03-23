package com.example.thrift.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrift.data.models.User
import com.example.thrift.repository.AuthRepository
import kotlinx.coroutines.launch

/**
 * SharedAuthViewModel manages authentication state and operations globally.
 * This is a singleton-like ViewModel that is shared across the entire app
 * using a factory, preventing multiple auth state checks.
 *
 * Key design decisions:
 * - Only checks auth state ONCE during initialization
 * - Uses a flag to prevent duplicate initialization
 * - Observers should use navigation flags to prevent duplicate navigation
 */
class SharedAuthViewModel(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableLiveData<AuthState>(AuthState.Loading)
    val authState: LiveData<AuthState> = _authState

    private val _currentUser = MutableLiveData<User?>()
    val currentUser: LiveData<User?> = _currentUser

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private var hasInitialized = false

    init {
        // Only initialize once to prevent multiple auth checks
        if (!hasInitialized) {
            hasInitialized = true
            checkCurrentUser()
        }
    }

    fun register(email: String, password: String, displayName: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.register(email, password, displayName)
            result.onSuccess { user ->
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
                _errorMessage.value = null
            }
            result.onFailure { exception ->
                _authState.value = AuthState.Unauthenticated
                _errorMessage.value = exception.message ?: "Registration failed"
            }
        }
    }

    fun login(email: String, password: String) {
        _authState.value = AuthState.Loading
        viewModelScope.launch {
            val result = authRepository.login(email, password)
            result.onSuccess { user ->
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
                _errorMessage.value = null
            }
            result.onFailure { exception ->
                _authState.value = AuthState.Unauthenticated
                _errorMessage.value = exception.message ?: "Login failed"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun sendPasswordReset(email: String) {
        viewModelScope.launch {
            val result = authRepository.sendPasswordResetEmail(email)
            result.onSuccess {
                _errorMessage.value = "Password reset email sent"
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to send password reset email"
            }
        }
    }

    fun logout() {
        authRepository.signOut()
        _currentUser.value = null
        _authState.value = AuthState.Unauthenticated
        _errorMessage.value = null
    }

    private fun checkCurrentUser() {
        viewModelScope.launch {
            val user = authRepository.getCurrentUser()
            if (user != null) {
                _currentUser.value = user
                _authState.value = AuthState.Authenticated
            } else {
                _authState.value = AuthState.Unauthenticated
            }
        }
    }

    sealed class AuthState {
        data object Unauthenticated : AuthState()
        data object Authenticated : AuthState()
        data object Loading : AuthState()
    }
}

