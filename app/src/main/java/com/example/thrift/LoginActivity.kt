package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize ViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnBackWelcome = findViewById<Button>(R.id.btnBackWelcome)
        val btnLoginNow = findViewById<Button>(R.id.btnLoginNow)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        // Observe authentication state
        authViewModel.authState.observe(this) { state ->
            when (state) {
                is AuthViewModel.AuthState.Loading -> {
                    btnLoginNow.isEnabled = false
                    btnLoginNow.text = "Logging in..."
                }
                is AuthViewModel.AuthState.Authenticated -> {
                    btnLoginNow.isEnabled = true
                    btnLoginNow.text = "Login"
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                is AuthViewModel.AuthState.Unauthenticated -> {
                    btnLoginNow.isEnabled = true
                    btnLoginNow.text = "Login"
                }
            }
        }

        // Observe error messages
        authViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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