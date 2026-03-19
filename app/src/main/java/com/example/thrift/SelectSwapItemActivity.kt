package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SelectSwapItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_swap_item)

        val rbSelectItemOne = findViewById<RadioButton>(R.id.rbSelectItemOne)
        val rbSelectItemTwo = findViewById<RadioButton>(R.id.rbSelectItemTwo)
        val rbSelectItemThree = findViewById<RadioButton>(R.id.rbSelectItemThree)
        val btnSendSwapRequest = findViewById<Button>(R.id.btnSendSwapRequest)

        val navHomeSelectSwap = findViewById<ImageButton>(R.id.navHomeSelectSwap)
        val navSwapSelectSwap = findViewById<ImageButton>(R.id.navSwapSelectSwap)
        val navCartSelectSwap = findViewById<ImageButton>(R.id.navCartSelectSwap)
        val navProfileSelectSwap = findViewById<ImageButton>(R.id.navProfileSelectSwap)

        val requestedItemName = intent.getStringExtra("requestedItemName") ?: "Requested Item"
        val requestedItemSize = intent.getStringExtra("requestedItemSize") ?: "N/A"
        val requestedItemCondition = intent.getStringExtra("requestedItemCondition") ?: "N/A"
        val requestedItemImage = intent.getIntExtra("requestedItemImage", R.drawable.bape_hoodie)

        rbSelectItemOne.setOnClickListener {
            rbSelectItemTwo.isChecked = false
            rbSelectItemThree.isChecked = false
        }

        rbSelectItemTwo.setOnClickListener {
            rbSelectItemOne.isChecked = false
            rbSelectItemThree.isChecked = false
        }

        rbSelectItemThree.setOnClickListener {
            rbSelectItemOne.isChecked = false
            rbSelectItemTwo.isChecked = false
        }

        btnSendSwapRequest.setOnClickListener {
            val intent = Intent(this, SwapReviewActivity::class.java)

            intent.putExtra("requestedItemName", requestedItemName)
            intent.putExtra("requestedItemSize", requestedItemSize)
            intent.putExtra("requestedItemCondition", requestedItemCondition)
            intent.putExtra("requestedItemImage", requestedItemImage)

            when {
                rbSelectItemOne.isChecked -> {
                    intent.putExtra("offeredItemName", "Cropped Knit Sweater")
                    intent.putExtra("offeredItemSize", "Medium")
                    intent.putExtra("offeredItemCondition", "8/10")
                    intent.putExtra("offeredItemImage", R.drawable.red_cropped_polo)
                    startActivity(intent)
                }

                rbSelectItemTwo.isChecked -> {
                    intent.putExtra("offeredItemName", "Denim Mini Skirt")
                    intent.putExtra("offeredItemSize", "Small")
                    intent.putExtra("offeredItemCondition", "9/10")
                    intent.putExtra("offeredItemImage", R.drawable.low_rise_jeans)
                    startActivity(intent)
                }

                rbSelectItemThree.isChecked -> {
                    intent.putExtra("offeredItemName", "Plaid Overshirt")
                    intent.putExtra("offeredItemSize", "Large")
                    intent.putExtra("offeredItemCondition", "8/10")
                    intent.putExtra("offeredItemImage", R.drawable.ny_shirt)
                    startActivity(intent)
                }

                else -> {
                    Toast.makeText(this, "Please select one of your items first", Toast.LENGTH_SHORT).show()
                }
            }
        }

        navHomeSelectSwap.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapSelectSwap.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartSelectSwap.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileSelectSwap.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}