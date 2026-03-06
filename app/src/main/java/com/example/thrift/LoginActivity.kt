package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnBackWelcome = findViewById<Button>(R.id.btnBackWelcome)
        val btnLoginNow = findViewById<Button>(R.id.btnLoginNow)
        val tvForgotPassword = findViewById<TextView>(R.id.tvForgotPassword)

        btnBackWelcome.setOnClickListener {
            finish()
        }

        btnLoginNow.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}