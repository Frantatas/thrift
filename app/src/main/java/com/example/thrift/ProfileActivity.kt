package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import android.widget.Toast

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnLogout = findViewById<Button>(R.id.btnLogout)
        val btnUploadNewItem = findViewById<Button>(R.id.btnUploadNewItem)
        val btnMySwapListings = findViewById<Button>(R.id.btnMySwapListings)

        val navHomeProfile = findViewById<ImageButton>(R.id.navHomeProfile)
        val navSwapProfile = findViewById<ImageButton>(R.id.navSwapProfile)
        val navCartProfile = findViewById<ImageButton>(R.id.navCartProfile)
        val navProfileProfile = findViewById<ImageButton>(R.id.navProfileProfile)

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        btnUploadNewItem.setOnClickListener {
            startActivity(Intent(this, UploadItemActivity::class.java))
        }

        btnMySwapListings.setOnClickListener {
            Toast.makeText(this, "My swap listings screen next", Toast.LENGTH_SHORT).show()
        }

        navHomeProfile.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapProfile.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartProfile.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileProfile.setOnClickListener {
            // already on profile
        }
    }
}