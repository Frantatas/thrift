package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class SwapActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        val btnRequestSwapOne = findViewById<Button>(R.id.btnRequestSwapOne)
        val btnRequestSwapTwo = findViewById<Button>(R.id.btnRequestSwapTwo)
        val btnRequestSwapThree = findViewById<Button>(R.id.btnRequestSwapThree)

        val navHomeSwap = findViewById<ImageButton>(R.id.navHomeSwap)
        val navSwapSwap = findViewById<ImageButton>(R.id.navSwapSwap)
        val navCartSwap = findViewById<ImageButton>(R.id.navCartSwap)
        val navProfileSwap = findViewById<ImageButton>(R.id.navProfileSwap)

        btnRequestSwapOne.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            startActivity(intent)
        }

        btnRequestSwapTwo.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            startActivity(intent)
        }

        btnRequestSwapThree.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            startActivity(intent)
        }

        navHomeSwap.setOnClickListener {
            val intent = Intent(this, DashboardActivity::class.java)
            startActivity(intent)
        }

        navSwapSwap.setOnClickListener {
            // already on swap
        }

        navCartSwap.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        navProfileSwap.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}