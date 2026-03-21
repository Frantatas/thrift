package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.thrift.viewmodel.SwapViewModel

class MySwapListingsActivity : AppCompatActivity() {

    private lateinit var swapViewModel: SwapViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_swap_listings)

        // Initialize ViewModel
        swapViewModel = ViewModelProvider(this).get(SwapViewModel::class.java)

        val listingsContainer = findViewById<LinearLayout>(R.id.listingsContainer)
        val tvEmptyListings = findViewById<TextView>(R.id.tvEmptyListings)

        val navHomeMyListings = findViewById<ImageButton>(R.id.navHomeMyListings)
        val navSwapMyListings = findViewById<ImageButton>(R.id.navSwapMyListings)
        val navCartMyListings = findViewById<ImageButton>(R.id.navCartMyListings)
        val navProfileMyListings = findViewById<ImageButton>(R.id.navProfileMyListings)

        // Load user's swap items from Firestore
        // Note: In a real app, you'd pass the current user ID from AuthViewModel
        swapViewModel.loadUserSwapItems("current_user_id")

        // Observe user's swap items
        swapViewModel.userSwapItems.observe(this) { listings ->
            listingsContainer.removeAllViews()

            if (listings.isEmpty()) {
                tvEmptyListings.visibility = TextView.VISIBLE
            } else {
                tvEmptyListings.visibility = TextView.GONE

                for (listing in listings) {
                    val card = LinearLayout(this).apply {
                        orientation = LinearLayout.VERTICAL
                        setPadding(16, 16, 16, 16)
                        background = getDrawable(R.drawable.rounded_card)

                        val params = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                        params.bottomMargin = 24
                        layoutParams = params
                    }

                    val title = TextView(this).apply {
                        text = listing.title
                        textSize = 18f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 16, 0, 0)
                    }

                    val size = TextView(this).apply {
                        text = "Size: ${listing.size}"
                        textSize = 14f
                        setTextColor(getColor(R.color.text_main))
                    }

                    val condition = TextView(this).apply {
                        text = "Condition: ${listing.condition}"
                        textSize = 14f
                        setTextColor(getColor(R.color.text_main))
                    }

                    val available = TextView(this).apply {
                        text = "Available: ${listing.isAvailable}"
                        textSize = 15f
                        setTextColor(getColor(R.color.text_main))
                        setPadding(0, 8, 0, 0)
                    }

                    card.addView(title)
                    card.addView(size)
                    card.addView(condition)
                    card.addView(available)

                    listingsContainer.addView(card)
                }
            }
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
                Toast.makeText(this, "Loading your listings...", Toast.LENGTH_SHORT).show()
            }
        }

        // For backward compatibility, also display local listings if ViewModel is empty
        if (MySwapListingManager.listings.isNotEmpty()) {
            listingsContainer.removeAllViews()
            tvEmptyListings.visibility = TextView.GONE

            for (listing in MySwapListingManager.listings) {
                val card = LinearLayout(this).apply {
                    orientation = LinearLayout.VERTICAL
                    setPadding(16, 16, 16, 16)
                    background = getDrawable(R.drawable.rounded_card)

                    val params = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.bottomMargin = 24
                    layoutParams = params
                }

                val title = TextView(this).apply {
                    text = listing.itemName
                    textSize = 18f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 16, 0, 0)
                }

                val size = TextView(this).apply {
                    text = "Size: ${listing.itemSize}"
                    textSize = 14f
                    setTextColor(getColor(R.color.text_main))
                }

                val condition = TextView(this).apply {
                    text = "Condition: ${listing.itemCondition}"
                    textSize = 14f
                    setTextColor(getColor(R.color.text_main))
                }

                val status = TextView(this).apply {
                    text = "Status: ${listing.status}"
                    textSize = 15f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 8, 0, 0)
                }

                card.addView(title)
                card.addView(size)
                card.addView(condition)
                card.addView(status)

                listingsContainer.addView(card)
            }
        }

        navHomeMyListings.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapMyListings.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartMyListings.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfileMyListings.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}