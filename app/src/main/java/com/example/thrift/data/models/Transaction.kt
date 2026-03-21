package com.example.thrift.data.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * Transaction model representing a purchase transaction in the Thrift marketplace.
 * Stored in Firestore under the 'transactions' collection.
 */
data class Transaction(
    val transactionId: String = "",
    val buyerId: String = "",
    val sellerId: String = "",
    val itemId: String = "",
    val itemTitle: String = "",
    val quantity: Int = 1,
    val totalPrice: Double = 0.0,
    val status: String = "", // "Pending", "Completed", "Cancelled"
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val completedAt: Date? = null
)

