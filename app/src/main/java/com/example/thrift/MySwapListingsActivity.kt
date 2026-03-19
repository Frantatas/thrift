package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MySwapListingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_swap_listings)

        val listingsContainer = findViewById<LinearLayout>(R.id.listingsContainer)
        val tvEmptyListings = findViewById<TextView>(R.id.tvEmptyListings)

        val navHomeMyListings = findViewById<ImageButton>(R.id.navHomeMyListings)
        val navSwapMyListings = findViewById<ImageButton>(R.id.navSwapMyListings)
        val navCartMyListings = findViewById<ImageButton>(R.id.navCartMyListings)
        val navProfileMyListings = findViewById<ImageButton>(R.id.navProfileMyListings)

        listingsContainer.removeAllViews()

        if (MySwapListingManager.listings.isEmpty()) {
            tvEmptyListings.visibility = TextView.VISIBLE
        } else {
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

                val image = ImageView(this).apply {
                    setImageResource(listing.itemImage)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        320
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setBackgroundColor(getColor(R.color.card_bg))
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

                card.addView(image)
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