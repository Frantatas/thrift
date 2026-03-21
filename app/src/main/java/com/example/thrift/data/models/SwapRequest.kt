package com.example.thrift.data.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * SwapRequest model representing a request to swap items between users.
 * Stored in Firestore under the 'swapRequests' collection.
 */
data class SwapRequest(
    val requestId: String = "",
    val requesterId: String = "",
    val requesterName: String = "",
    val recipientId: String = "",
    val recipientName: String = "",
    val requesterItemId: String = "",
    val requesterItemTitle: String = "",
    val requestedItemId: String = "",
    val requestedItemTitle: String = "",
    val status: String = "", // "Pending", "Accepted", "Declined", "Completed"
    val message: String? = null,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val respondedAt: Date? = null
)

