package com.example.thrift

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBackFromRegister = findViewById<Button>(R.id.btnBackFromRegister)

        btnBackFromRegister.setOnClickListener {
            finish()
        }
    }
}