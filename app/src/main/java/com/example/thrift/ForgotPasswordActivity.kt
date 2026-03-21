package com.example.thrift

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.AuthViewModel

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        // Initialize ViewModel
        authViewModel = ViewModelProvider(this).get(AuthViewModel::class.java)

        val etForgotEmail = findViewById<EditText>(R.id.etForgotEmail)
        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)
        val btnSendReset = findViewById<Button>(R.id.btnSendReset)

        // Observe error messages which includes success messages
        authViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        btnBackToLogin.setOnClickListener {
            finish()
        }

        btnSendReset.setOnClickListener {
            val email = etForgotEmail.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                authViewModel.sendPasswordReset(email)
                etForgotEmail.text.clear()
            }
        }
    }
}