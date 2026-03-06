package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        val navHomeCart = findViewById<ImageButton>(R.id.navHomeCart)
        val navSwapCart = findViewById<ImageButton>(R.id.navSwapCart)
        val navCartCart = findViewById<ImageButton>(R.id.navCartCart)
        val navProfileCart = findViewById<ImageButton>(R.id.navProfileCart)

        btnCheckout.setOnClickListener {
            Toast.makeText(this, "Checkout flow next", Toast.LENGTH_SHORT).show()
        }

        navHomeCart.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        navSwapCart.setOnClickListener {
            val intent = Intent(this, SwapActivity::class.java)
            startActivity(intent)
        }

        navCartCart.setOnClickListener {
            // already on cart
        }

        navProfileCart.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}