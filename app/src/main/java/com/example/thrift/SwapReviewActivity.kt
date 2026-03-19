package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SwapReviewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_review)

        val ivRequestedItem = findViewById<ImageView>(R.id.ivRequestedItem)
        val tvRequestedItemName = findViewById<TextView>(R.id.tvRequestedItemName)
        val tvRequestedItemSize = findViewById<TextView>(R.id.tvRequestedItemSize)
        val tvRequestedItemCondition = findViewById<TextView>(R.id.tvRequestedItemCondition)

        val ivOfferedItem = findViewById<ImageView>(R.id.ivOfferedItem)
        val tvOfferedItemName = findViewById<TextView>(R.id.tvOfferedItemName)
        val tvOfferedItemSize = findViewById<TextView>(R.id.tvOfferedItemSize)
        val tvOfferedItemCondition = findViewById<TextView>(R.id.tvOfferedItemCondition)

        val etSwapMessage = findViewById<EditText>(R.id.etSwapMessage)
        val btnSendSwapRequest = findViewById<Button>(R.id.btnSendSwapRequest)

        val navHomeSwapReview = findViewById<ImageButton>(R.id.navHomeSwapReview)
        val navSwapSwapReview = findViewById<ImageButton>(R.id.navSwapSwapReview)
        val navCartSwapReview = findViewById<ImageButton>(R.id.navCartSwapReview)
        val navProfileSwapReview = findViewById<ImageButton>(R.id.navProfileSwapReview)

        val requestedItemName = intent.getStringExtra("requestedItemName") ?: "Requested Item"
        val requestedItemSize = intent.getStringExtra("requestedItemSize") ?: "N/A"
        val requestedItemCondition = intent.getStringExtra("requestedItemCondition") ?: "N/A"
        val requestedItemImage = intent.getIntExtra("requestedItemImage", R.drawable.bape_hoodie)

        val offeredItemName = intent.getStringExtra("offeredItemName") ?: "Your Item"
        val offeredItemSize = intent.getStringExtra("offeredItemSize") ?: "N/A"
        val offeredItemCondition = intent.getStringExtra("offeredItemCondition") ?: "N/A"
        val offeredItemImage = intent.getIntExtra("offeredItemImage", R.drawable.bape_hoodie)

        ivRequestedItem.setImageResource(requestedItemImage)
        tvRequestedItemName.text = requestedItemName
        tvRequestedItemSize.text = "Size: $requestedItemSize"
        tvRequestedItemCondition.text = "Condition: $requestedItemCondition"

        ivOfferedItem.setImageResource(offeredItemImage)
        tvOfferedItemName.text = offeredItemName
        tvOfferedItemSize.text = "Size: $offeredItemSize"
        tvOfferedItemCondition.text = "Condition: $offeredItemCondition"

        btnSendSwapRequest.setOnClickListener {
            val message = etSwapMessage.text.toString().trim()

            if (message.isEmpty()) {
                Toast.makeText(this, "Add a short message first", Toast.LENGTH_SHORT).show()
            } else {
                val request = SwapRequest(
                    requestedItemName = requestedItemName,
                    requestedItemSize = requestedItemSize,
                    requestedItemCondition = requestedItemCondition,
                    requestedItemImage = requestedItemImage,
                    offeredItemName = offeredItemName,
                    offeredItemSize = offeredItemSize,
                    offeredItemCondition = offeredItemCondition,
                    offeredItemImage = offeredItemImage,
                    message = message,
                    status = "Pending"
                )

                SwapRequestManager.addRequest(request)

                Toast.makeText(this, "Swap request sent", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, SwapRequestsActivity::class.java))
                finish()
            }
        }

        navHomeSwapReview.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapSwapReview.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartSwapReview.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileSwapReview.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}