package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SharedAuthViewModel
import com.example.thrift.viewmodel.SharedAuthViewModelFactory

class RegisterActivity : AppCompatActivity() {

    private lateinit var authViewModel: SharedAuthViewModel
    private var hasNavigatedToDashboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Initialize SharedAuthViewModel using shared instance
        authViewModel = SharedAuthViewModelFactory.getInstance()

        val etFullName = findViewById<EditText>(R.id.etFullName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnBackFromRegister = findViewById<Button>(R.id.btnBackFromRegister)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        // Observe authentication state - ONLY trigger on explicit registration, not on init
        authViewModel.authState.observe(this) { state ->
            when (state) {
                is SharedAuthViewModel.AuthState.Loading -> {
                    btnCreateAccount.isEnabled = false
                    btnCreateAccount.text = "Creating..."
                }
                is SharedAuthViewModel.AuthState.Authenticated -> {
                    btnCreateAccount.isEnabled = true
                    btnCreateAccount.text = "Create Account"
                    Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                    // Only navigate once to prevent duplicate transitions
                    // But only if we actually just performed a registration (not on init)
                    if (!hasNavigatedToDashboard && etEmail.text.toString().isNotEmpty()) {
                        hasNavigatedToDashboard = true
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                is SharedAuthViewModel.AuthState.Unauthenticated -> {
                    btnCreateAccount.isEnabled = true
                    btnCreateAccount.text = "Create Account"
                }
            }
        }

        // Observe error messages
        authViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                // Clear error after showing
                authViewModel.clearError()
            }
        }

        btnBackFromRegister.setOnClickListener {
            finish()
        }

        btnCreateAccount.setOnClickListener {
            val fullName = etFullName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (fullName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else if (password.length < 6) {
                Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.register(email, password, fullName)
            }
        }
    }
}