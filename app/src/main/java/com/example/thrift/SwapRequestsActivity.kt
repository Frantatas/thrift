package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources

class SwapRequestsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_requests)

        val requestsContainer = findViewById<LinearLayout>(R.id.requestsContainer)
        val tvEmptyRequests = findViewById<TextView>(R.id.tvEmptyRequests)

        val navHomeSwapRequests = findViewById<ImageButton>(R.id.navHomeSwapRequests)
        val navSwapSwapRequests = findViewById<ImageButton>(R.id.navSwapSwapRequests)
        val navCartSwapRequests = findViewById<ImageButton>(R.id.navCartSwapRequests)
        val navProfileSwapRequests = findViewById<ImageButton>(R.id.navProfileSwapRequests)

        requestsContainer.removeAllViews()

        if (SwapRequestManager.swapRequests.isEmpty()) {
            tvEmptyRequests.visibility = TextView.VISIBLE
        } else {
            tvEmptyRequests.visibility = TextView.GONE

            for (request in SwapRequestManager.swapRequests) {
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                    background = AppCompatResources.getDrawable(this@SwapRequestsActivity, R.drawable.rounded_card)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.bottomMargin = 24
                    layoutParams = params
                }

                val requestedImage = ImageView(this).apply {
                    setImageResource(request.requestedItemImage)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        320
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setBackgroundColor(getColor(R.color.card_bg))
                }

                val requestedTitle = TextView(this).apply {
                    text = getString(R.string.requested_item_format, request.requestedItemName)
                    textSize = 17f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 16, 0, 0)
                }

                val requestedMeta = TextView(this).apply {
                    text = getString(
                        R.string.request_meta_format,
                        request.requestedItemSize,
                        request.requestedItemCondition
                    )
                    textSize = 14f
                    setTextColor(getColor(R.color.text_main))
                }

                val offeredImage = ImageView(this).apply {
                    setImageResource(request.offeredItemImage)
                    val imageParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        320
                    )
                    imageParams.topMargin = 20
                    layoutParams = imageParams
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setBackgroundColor(getColor(R.color.card_bg))
                }

                val offeredTitle = TextView(this).apply {
                    text = getString(R.string.offered_item_format, request.offeredItemName)
                    textSize = 17f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 16, 0, 0)
                }

                val offeredMeta = TextView(this).apply {
                    text = getString(
                        R.string.request_meta_format,
                        request.offeredItemSize,
                        request.offeredItemCondition
                    )
                    textSize = 14f
                    setTextColor(getColor(R.color.text_main))
                }

                val message = TextView(this).apply {
                    text = getString(R.string.request_message_format, request.message)
                    textSize = 14f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 16, 0, 0)
                }

                val status = TextView(this).apply {
                    text = getString(R.string.request_status_format, request.status)
                    textSize = 15f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 8, 0, 0)
                }

                card.addView(requestedImage)
                card.addView(requestedTitle)
                card.addView(requestedMeta)
                card.addView(offeredImage)
                card.addView(offeredTitle)
                card.addView(offeredMeta)
                card.addView(message)
                card.addView(status)

                requestsContainer.addView(card)
            }
        }

        navHomeSwapRequests.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapSwapRequests.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartSwapRequests.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileSwapRequests.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}