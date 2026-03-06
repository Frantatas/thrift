package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UploadItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_item)

        val btnChooseImage = findViewById<Button>(R.id.btnChooseImage)
        val btnPublishItem = findViewById<Button>(R.id.btnPublishItem)

        val navHomeUpload = findViewById<ImageButton>(R.id.navHomeUpload)
        val navSwapUpload = findViewById<ImageButton>(R.id.navSwapUpload)
        val navCartUpload = findViewById<ImageButton>(R.id.navCartUpload)
        val navProfileUpload = findViewById<ImageButton>(R.id.navProfileUpload)

        btnChooseImage.setOnClickListener {
            Toast.makeText(this, "Image picker next", Toast.LENGTH_SHORT).show()
        }

        btnPublishItem.setOnClickListener {
            Toast.makeText(this, "Item published", Toast.LENGTH_SHORT).show()
        }

        navHomeUpload.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapUpload.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartUpload.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileUpload.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}