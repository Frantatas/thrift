package com.example.thrift.data.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Item model representing a thrift item for sale.
 * Stored in Firestore under the 'items' collection.
 */
data class Item(
    val itemId: String = "",
    val sellerId: String = "",
    val sellerName: String = "",
    val title: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val condition: String = "", // "Excellent", "Good", "Fair", "Poor"
    val category: String = "", // e.g., "Tops", "Pants", "Dresses", etc.
    val size: String = "",
    val color: String = "",
    val imageUrls: List<String> = emptyList(),
    val isAvailable: Boolean = true,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

