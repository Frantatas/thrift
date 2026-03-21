package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SwapViewModel

class SwapActivity : AppCompatActivity() {

    private lateinit var swapViewModel: SwapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap)

        // Initialize ViewModel
        swapViewModel = ViewModelProvider(this).get(SwapViewModel::class.java)

        val btnRequestSwapOne = findViewById<Button>(R.id.btnRequestSwapOne)
        val btnRequestSwapTwo = findViewById<Button>(R.id.btnRequestSwapTwo)
        val btnRequestSwapThree = findViewById<Button>(R.id.btnRequestSwapThree)

        val navHomeSwap = findViewById<ImageButton>(R.id.navHomeSwap)
        val navSwapSwap = findViewById<ImageButton>(R.id.navSwapSwap)
        val navCartSwap = findViewById<ImageButton>(R.id.navCartSwap)
        val navProfileSwap = findViewById<ImageButton>(R.id.navProfileSwap)

        // Load all swap items from Firestore
        swapViewModel.loadAllSwapItems()

        // Observe swap items
        swapViewModel.allSwapItems.observe(this) { swapItems ->
            // Update UI with swap items from Firestore
            // For now, keep the existing button-based flow
        }

        // Observe errors
        swapViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        // Observe loading state
        swapViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading) {
                Toast.makeText(this, "Loading swap items...", Toast.LENGTH_SHORT).show()
            }
        }

        btnRequestSwapOne.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            intent.putExtra("requestedItemName", "Cropped Knit Sweater")
            intent.putExtra("requestedItemSize", "Medium")
            intent.putExtra("requestedItemCondition", "8/10")
            intent.putExtra("requestedItemImage", R.drawable.red_cropped_polo)
            startActivity(intent)
        }

        btnRequestSwapTwo.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            intent.putExtra("requestedItemName", "Denim Mini Skirt")
            intent.putExtra("requestedItemSize", "Small")
            intent.putExtra("requestedItemCondition", "9/10")
            intent.putExtra("requestedItemImage", R.drawable.low_rise_jeans)
            startActivity(intent)
        }

        btnRequestSwapThree.setOnClickListener {
            val intent = Intent(this, SelectSwapItemActivity::class.java)
            intent.putExtra("requestedItemName", "Plaid Overshirt")
            intent.putExtra("requestedItemSize", "Large")
            intent.putExtra("requestedItemCondition", "8/10")
            intent.putExtra("requestedItemImage", R.drawable.ny_shirt)
            startActivity(intent)
        }

        navHomeSwap.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapSwap.setOnClickListener {
            // already on swap
        }

        navCartSwap.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileSwap.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}