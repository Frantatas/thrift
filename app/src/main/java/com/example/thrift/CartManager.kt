package com.example.thrift

object CartManager {
    val cartItems = mutableListOf<Item>()

    fun addItem(item: Item) {
        val existingItem = cartItems.find { it.name == item.name }

        if (existingItem != null) {
            existingItem.quantity += 1
        } else {
            cartItems.add(item)
        }
    }

    fun clearCart() {
        cartItems.clear()
    }

    fun getSubtotal(): Int {
        var total = 0

        for (item in cartItems) {
            val numericPrice = item.price.replace("₱", "").replace(",", "").trim()
            val priceValue = numericPrice.toIntOrNull() ?: 0
            total += priceValue * item.quantity
        }

        return total
    }

    fun getShipping(): Int {
        return if (cartItems.isEmpty()) 0 else 80
    }

    fun getTotal(): Int {
        return getSubtotal() + getShipping()
    }
}