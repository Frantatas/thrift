package com.example.thrift

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val btnBackToLogin = findViewById<Button>(R.id.btnBackToLogin)

        btnBackToLogin.setOnClickListener {
            finish()
        }
    }
}