package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ItemDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)

        val ivDetailImage = findViewById<ImageView>(R.id.ivDetailImage)
        val tvDetailName = findViewById<TextView>(R.id.tvDetailName)
        val tvDetailPrice = findViewById<TextView>(R.id.tvDetailPrice)
        val tvDetailSize = findViewById<TextView>(R.id.tvDetailSize)
        val tvDetailCondition = findViewById<TextView>(R.id.tvDetailCondition)
        val tvDetailDescription = findViewById<TextView>(R.id.tvDetailDescription)

        val btnAddToCart = findViewById<Button>(R.id.btnAddToCart)
        val btnRequestSwapDetail = findViewById<Button>(R.id.btnRequestSwapDetail)

        val navHomeDetail = findViewById<ImageButton>(R.id.navHomeDetail)
        val navSwapDetail = findViewById<ImageButton>(R.id.navSwapDetail)
        val navCartDetail = findViewById<ImageButton>(R.id.navCartDetail)
        val navProfileDetail = findViewById<ImageButton>(R.id.navProfileDetail)

        val itemName = intent.getStringExtra("itemName") ?: "Unknown Item"
        val itemPrice = intent.getStringExtra("itemPrice") ?: "₱0"
        val itemSize = intent.getStringExtra("itemSize") ?: "N/A"
        val itemCondition = intent.getStringExtra("itemCondition") ?: "N/A"
        val itemDescription = intent.getStringExtra("itemDescription") ?: "No description available."
        val itemImage = intent.getIntExtra("itemImage", R.drawable.bape_hoodie)

        ivDetailImage.setImageResource(itemImage)
        tvDetailName.text = itemName
        tvDetailPrice.text = itemPrice
        tvDetailSize.text = "Size: $itemSize"
        tvDetailCondition.text = "Condition: $itemCondition"
        tvDetailDescription.text = itemDescription

        btnAddToCart.setOnClickListener {
            val item = Item(
                name = itemName,
                price = itemPrice,
                size = itemSize,
                condition = itemCondition,
                description = itemDescription,
                imageResId = itemImage,
                quantity = 1
            )

            CartManager.addItem(item)
            Toast.makeText(this, "$itemName added to cart", Toast.LENGTH_SHORT).show()
        }

        btnRequestSwapDetail.setOnClickListener {
            startActivity(Intent(this, SelectSwapItemActivity::class.java))
        }

        navHomeDetail.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapDetail.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartDetail.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileDetail.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}