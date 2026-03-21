package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SwapViewModel

class SwapRequestsActivity : AppCompatActivity() {

    private lateinit var swapViewModel: SwapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swap_requests)

        // Initialize ViewModel
        swapViewModel = ViewModelProvider(this).get(SwapViewModel::class.java)

        val requestsContainer = findViewById<LinearLayout>(R.id.requestsContainer)
        val tvEmptyRequests = findViewById<TextView>(R.id.tvEmptyRequests)

        val navHomeSwapRequests = findViewById<ImageButton>(R.id.navHomeSwapRequests)
        val navSwapSwapRequests = findViewById<ImageButton>(R.id.navSwapSwapRequests)
        val navCartSwapRequests = findViewById<ImageButton>(R.id.navCartSwapRequests)
        val navProfileSwapRequests = findViewById<ImageButton>(R.id.navProfileSwapRequests)

        // Load received swap requests from Firestore
        swapViewModel.loadAllSwapItems()

        // Observe received swap requests
        swapViewModel.receivedSwapRequests.observe(this) { requests ->
            requestsContainer.removeAllViews()

            if (requests.isEmpty()) {
                tvEmptyRequests.visibility = TextView.VISIBLE
            } else {
                tvEmptyRequests.visibility = TextView.GONE

                for (request in requests) {
                    val card = LinearLayout(this).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(16, 16, 16, 16)
                        background = AppCompatResources.getDrawable(
                            this@SwapRequestsActivity,
                            R.drawable.rounded_card
                        )

                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin = 24
                        layoutParams = params
                    }

                    val requestedTitle = TextView(this).apply {
                        text = "Requested: ${request.requestedItemTitle}"
                        textSize = 17f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 16, 0, 0)
                    }

                    val offeredTitle = TextView(this).apply {
                        text = "Offered: ${request.requesterItemTitle}"
                        textSize = 17f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 16, 0, 0)
                    }

                    val message = TextView(this).apply {
                        text = "Message: ${request.message ?: "No message"}"
                        textSize = 14f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 16, 0, 0)
                    }

                    val status = TextView(this).apply {
                        text = "Status: ${request.status}"
                        textSize = 15f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 8, 0, 0)
                    }

                    card.addView(requestedTitle)
                    card.addView(offeredTitle)
                    card.addView(message)
                    card.addView(status)

                    requestsContainer.addView(card)
                }
            }
        }

        // Observe errors
        swapViewModel.errorMessage.observe(this) { message ->
            if (!message.isNullOrEmpty()) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

        // For backward compatibility, also observe local swap requests
        if (SwapRequestManager.swapRequests.isNotEmpty()) {
            requestsContainer.removeAllViews()
            tvEmptyRequests.visibility = TextView.GONE

            for (request in SwapRequestManager.swapRequests) {
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                    background = AppCompatResources.getDrawable(
                        this@SwapRequestsActivity,
                        R.drawable.rounded_card
                    )

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.bottomMargin = 24
                    layoutParams = params
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

                card.addView(requestedTitle)
                card.addView(requestedMeta)
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