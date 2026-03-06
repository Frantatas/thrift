package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val recyclerItems = findViewById<RecyclerView>(R.id.recyclerItems)

        val items = listOf(
            Item(
                "BAPE Hoodie",
                "₱850",
                "Medium",
                "8/10",
                "Cozy streetwear hoodie with a bold printed design.",
                R.drawable.bape_hoodie
            ),
            Item(
                "Jean Jacket",
                "₱1100",
                "Large",
                "9/10",
                "Classic thrifted denim jacket for everyday layering.",
                R.drawable.jean_jacket
            ),
            Item(
                "Low Rise Jeans",
                "₱780",
                "Small",
                "8/10",
                "Vintage low rise jeans with a relaxed fit.",
                R.drawable.low_rise_jeans
            ),
            Item(
                "NY Shirt",
                "₱420",
                "Medium",
                "9/10",
                "Casual printed shirt with a sporty city vibe.",
                R.drawable.ny_shirt
            ),
            Item(
                "Red Cropped Polo",
                "₱550",
                "Small",
                "9/10",
                "Cute cropped polo top in a bright red tone.",
                R.drawable.red_cropped_polo
            ),
            Item(
                "Spiderman Shirt",
                "₱480",
                "Medium",
                "9/10",
                "Graphic tee with a fun comic-inspired design.",
                R.drawable.spiderman_shirt
            )
        )

        recyclerItems.layoutManager = GridLayoutManager(this, 2)

        recyclerItems.adapter = ItemAdapter(items) { item ->
            val intent = Intent(this, ItemDetailActivity::class.java)
            intent.putExtra("itemName", item.name)
            intent.putExtra("itemPrice", item.price)
            intent.putExtra("itemSize", item.size)
            intent.putExtra("itemCondition", item.condition)
            intent.putExtra("itemDescription", item.description)
            intent.putExtra("itemImage", item.imageResId)
            startActivity(intent)
        }

        val navHome = findViewById<ImageButton>(R.id.navHome)
        val navSwap = findViewById<ImageButton>(R.id.navSwap)
        val navCart = findViewById<ImageButton>(R.id.navCart)
        val navProfile = findViewById<ImageButton>(R.id.navProfile)

        navHome.setOnClickListener {
            // already on dashboard
        }

        navSwap.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCart.setOnClickListener {
            startActivity(Intent(this, CartActivity::class.java))
        }

        navProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}