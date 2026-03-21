package com.example.thrift.data.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * SwapItem model representing an item available for swap.
 * Stored in Firestore under the 'swapItems' collection.
 */
data class SwapItem(
    val swapItemId: String = "",
    val userId: String = "",
    val userName: String = "",
    val title: String = "",
    val description: String = "",
    val condition: String = "", // "Excellent", "Good", "Fair", "Poor"
    val category: String = "",
    val size: String = "",
    val color: String = "",
    val imageUrls: List<String> = emptyList(),
    val isAvailable: Boolean = true,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

