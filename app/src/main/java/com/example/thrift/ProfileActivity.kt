package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val btnUploadNewItem = findViewById<Button>(R.id.btnUploadNewItem)
        val btnMySwapListings = findViewById<Button>(R.id.btnMySwapListings)
        val btnSwapRequests = findViewById<Button>(R.id.btnSwapRequests)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        val navHomeProfile = findViewById<ImageButton>(R.id.navHomeProfile)
        val navSwapProfile = findViewById<ImageButton>(R.id.navSwapProfile)
        val navCartProfile = findViewById<ImageButton>(R.id.navCartProfile)
        val navProfileProfile = findViewById<ImageButton>(R.id.navProfileProfile)

        btnUploadNewItem.setOnClickListener {
            startActivity(Intent(this, UploadItemActivity::class.java))
        }

        btnMySwapListings.setOnClickListener {
            startActivity(Intent(this, MySwapListingsActivity::class.java))
        }

        btnSwapRequests.setOnClickListener {
            Toast.makeText(this, "Opening swap requests...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SwapRequestsActivity::class.java))
        }

        btnLogout.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
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