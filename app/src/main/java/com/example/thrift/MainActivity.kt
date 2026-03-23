package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SharedAuthViewModel
import com.example.thrift.viewmodel.SharedAuthViewModelFactory

/**
 * MainActivity - Main entry point for the Thrift application.
 *
 * This activity serves as the welcome/splash screen and provides
 * navigation to the login and registration screens.
 *
 * On app startup:
 * 1. FirebaseInitializer automatically creates Firestore collections
 * 2. MainActivity displays with Login and Sign Up options
 * 3. Users can proceed to authentication
 */
class MainActivity : AppCompatActivity() {

    private lateinit var authViewModel: SharedAuthViewModel
    private var btnLogin: Button? = null
    private var btnSignUp: Button? = null
    private var hasNavigatedToDashboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            setContentView(R.layout.activity_main)
        } catch (e: Exception) {
            showError("Failed to load layout: ${e.message}")
            finish()
            return
        }

        // Initialize SharedAuthViewModel using shared instance
        try {
            authViewModel = SharedAuthViewModelFactory.getInstance()
        } catch (e: Exception) {
            showError("Authentication initialization failed: ${e.message}")
            return
        }

        // Check if user is already logged in before setting up UI
        // Only navigate once to prevent multiple refreshes
        authViewModel.authState.observe(this) { state ->
            when (state) {
                is SharedAuthViewModel.AuthState.Authenticated -> {
                    if (!hasNavigatedToDashboard) {
                        hasNavigatedToDashboard = true
                        navigateToDashboard()
                    }
                }
                is SharedAuthViewModel.AuthState.Unauthenticated -> {
                    // Reset flag when user is logged out - allows re-login
                    hasNavigatedToDashboard = false
                }
                else -> {
                    // Do nothing for Loading state
                }
            }
        }

        // Setup UI elements
        try {
            btnLogin = findViewById(R.id.btnLogin)
            btnSignUp = findViewById(R.id.btnSignUp)

            // Verify buttons were found
            if (btnLogin == null || btnSignUp == null) {
                showError("UI elements not found in layout")
                return
            }

            // Login button click listener
            btnLogin?.setOnClickListener {
                navigateToLogin()
            }

            // Sign Up button click listener
            btnSignUp?.setOnClickListener {
                navigateToRegister()
            }
        } catch (e: Exception) {
            showError("Error setting up UI: ${e.message}")
        }
    }

    /**
     * Navigate to LoginActivity
     */
    private fun navigateToLogin() {
        try {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } catch (_: ClassNotFoundException) {
            showError("LoginActivity not found")
        } catch (e: Exception) {
            showError("Failed to open login: ${e.message}")
        }
    }

    /**
     * Navigate to RegisterActivity
     */
    private fun navigateToRegister() {
        try {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        } catch (_: ClassNotFoundException) {
            showError("RegisterActivity not found")
        } catch (e: Exception) {
            showError("Failed to open registration: ${e.message}")
        }
    }

    /**
     * Navigate to Dashboard if user is authenticated
     */
    private fun navigateToDashboard() {
        try {
            val intent = Intent(this, DashboardActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } catch (_: ClassNotFoundException) {
            showError("DashboardActivity not found")
        } catch (e: Exception) {
            showError("Error navigating to dashboard: ${e.message}")
        }
    }

    /**
     * Show error message to user
     */
    private fun showError(message: String) {
        try {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // Silently fail if Toast fails
            e.printStackTrace()
        }
    }
}

