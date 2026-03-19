package com.example.thrift

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val cartItemsContainer = findViewById<LinearLayout>(R.id.cartItemsContainer)
        val tvEmptyCart = findViewById<TextView>(R.id.tvEmptyCart)
        val tvSubtotal = findViewById<TextView>(R.id.tvSubtotal)
        val tvShipping = findViewById<TextView>(R.id.tvShipping)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnCheckout = findViewById<Button>(R.id.btnCheckout)

        val navHomeCart = findViewById<ImageButton>(R.id.navHomeCart)
        val navSwapCart = findViewById<ImageButton>(R.id.navSwapCart)
        val navCartCart = findViewById<ImageButton>(R.id.navCartCart)
        val navProfileCart = findViewById<ImageButton>(R.id.navProfileCart)

        cartItemsContainer.removeAllViews()

        if (CartManager.cartItems.isEmpty()) {
            tvEmptyCart.visibility = TextView.VISIBLE
        } else {
            tvEmptyCart.visibility = TextView.GONE

            for (item in CartManager.cartItems) {
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
                    setImageResource(item.imageResId)
                    layoutParams = LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        350
                    )
                    scaleType = ImageView.ScaleType.CENTER_CROP
                    setBackgroundColor(getColor(R.color.card_bg))
                }

                val name = TextView(this).apply {
                    text = item.name
                    textSize = 17f
                    setTextColor(getColor(R.color.text_main))
                    setPadding(0, 16, 0, 0)
                }

                val price = TextView(this).apply {
                    text = item.price
                    textSize = 15f
                    setTextColor(getColor(R.color.text_main))
                }

                val quantity = TextView(this).apply {
                    text = "Quantity: ${item.quantity}"
                    textSize = 15f
                    setTextColor(getColor(R.color.text_main))
                }

                val itemTotal = TextView(this).apply {
                    val numericPrice = item.price.replace("₱", "").replace(",", "").trim()
                    val priceValue = numericPrice.toIntOrNull() ?: 0
                    text = "Item Total: ₱${priceValue * item.quantity}"
                    textSize = 15f
                    setTextColor(getColor(R.color.text_main))
                }

                card.addView(image)
                card.addView(name)
                card.addView(price)
                card.addView(quantity)
                card.addView(itemTotal)

                cartItemsContainer.addView(card)
            }
        }

        tvSubtotal.text = "Subtotal: ₱${CartManager.getSubtotal()}"
        tvShipping.text = "Shipping: ₱${CartManager.getShipping()}"
        tvTotal.text = "Total: ₱${CartManager.getTotal()}"

        btnCheckout.setOnClickListener {
            if (CartManager.cartItems.isEmpty()) {
                Toast.makeText(this, "Your cart is empty", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Checkout complete", Toast.LENGTH_SHORT).show()
                CartManager.clearCart()
                startActivity(Intent(this, CartActivity::class.java))
                finish()
            }
        }

        navHomeCart.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java))
        }

        navSwapCart.setOnClickListener {
            startActivity(Intent(this, SwapActivity::class.java))
        }

        navCartCart.setOnClickListener {
            // already on cart
        }

        navProfileCart.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}