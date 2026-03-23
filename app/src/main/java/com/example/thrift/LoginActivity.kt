package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SharedAuthViewModel
import com.example.thrift.viewmodel.SharedAuthViewModelFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: SharedAuthViewModel
    private var hasNavigatedToDashboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedAuthViewModel using shared instance
        authViewModel = SharedAuthViewModelFactory.getInstance()

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnBackWelcome = findViewById<Button>(R.id.btnBackWelcome)
        val btnLoginNow = findViewById<Button>(R.id.btnLoginNow)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Observe authentication state - ONLY trigger on explicit login, not on init
        authViewModel.authState.observe(this) { state ->
            when (state) {
                is SharedAuthViewModel.AuthState.Loading -> {
                    btnLoginNow.isEnabled = false
                    btnLoginNow.text = "Logging in..."
                }
                is SharedAuthViewModel.AuthState.Authenticated -> {
                    btnLoginNow.isEnabled = true
                    btnLoginNow.text = "Login"
                    // Only navigate once to prevent duplicate transitions
                    // But only if we actually just performed a login (not on init)
                    if (!hasNavigatedToDashboard && etEmail.text.toString().isNotEmpty()) {
                        hasNavigatedToDashboard = true
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                is SharedAuthViewModel.AuthState.Unauthenticated -> {
                    btnLoginNow.isEnabled = true
                    btnLoginNow.text = "Login"
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

        btnBackWelcome.setOnClickListener {
            finish()
        }

        btnLoginNow.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.login(email, password)
            }
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}