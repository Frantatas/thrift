package com.example.thrift.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.repository.AuthRepository

/**
 * Factory for creating AuthViewModel instances.
 * Ensures that the SAME instance is reused across the entire app.
 * This is a singleton pattern that persists auth state across activities.
 */
class SharedAuthViewModelFactory(
    private val authRepository: AuthRepository = AuthRepository()
) : ViewModelProvider.Factory {

    companion object {
        private var sharedInstance: SharedAuthViewModel? = null

        fun getInstance(authRepository: AuthRepository = AuthRepository()): SharedAuthViewModel {
            if (sharedInstance == null) {
                sharedInstance = SharedAuthViewModel(authRepository)
            }
            return sharedInstance!!
        }

        fun resetInstance() {
            sharedInstance = null
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(SharedAuthViewModel::class.java) -> {
                getInstance(authRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}

