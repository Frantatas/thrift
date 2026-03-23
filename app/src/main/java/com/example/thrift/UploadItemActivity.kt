package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.data.models.Item
import com.example.thrift.viewmodel.SharedAuthViewModel
import com.example.thrift.viewmodel.SharedAuthViewModelFactory
import com.example.thrift.viewmodel.ItemViewModel
import java.util.UUID

class UploadItemActivity : AppCompatActivity() {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var authViewModel: SharedAuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_item)

        // Initialize ViewModels - use shared auth instance
        itemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        authViewModel = SharedAuthViewModelFactory.getInstance()

        val etItemName = findViewById<EditText>(R.id.etItemName)
        val btnChooseImage = findViewById<Button>(R.id.btnChooseImage)
        val btnPublishItem = findViewById<Button>(R.id.btnPublishItem)

        val navHomeUpload = findViewById<ImageButton>(R.id.navHomeUpload)
        val navSwapUpload = findViewById<ImageButton>(R.id.navSwapUpload)
        val navCartUpload = findViewById<ImageButton>(R.id.navCartUpload)
        val navProfileUpload = findViewById<ImageButton>(R.id.navProfileUpload)

        // Observe loading state
        itemViewModel.isLoading.observe(this) { isLoading ->
            btnPublishItem.isEnabled = !isLoading
            btnPublishItem.text = if (isLoading) "Publishing..." else "Publish Item"
        }

        // Observe success/error messages
        itemViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (message.contains("successfully", ignoreCase = true)) {
                    // Clear form after successful upload
                    etItemName.text.clear()
                }
            }
        }

        btnChooseImage.setOnClickListener {
            Toast.makeText(this, "Image picker coming soon", Toast.LENGTH_SHORT).show()
        }

        btnPublishItem.setOnClickListener {
            val itemName = etItemName.text.toString().trim()

            if (itemName.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // Get current user ID from AuthViewModel
                val currentUserId = authViewModel.currentUser.value?.uid ?: "anonymous"
                val currentUserName = authViewModel.currentUser.value?.displayName ?: "Anonymous User"

                val newItem = Item(
                    itemId = UUID.randomUUID().toString(),
                    sellerId = currentUserId,
                    sellerName = currentUserName,
                    title = itemName,
                    description = "No description provided",
                    price = 0.0,
                    condition = "Good",
                    category = "Other",
                    size = "One Size",
                    color = "Unknown",
                    imageUrls = emptyList(),
                    isAvailable = true
                )
                itemViewModel.createItem(newItem)
            }
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