package com.example.thrift.data.models

import com.google.firebase.firestore.ServerTimestamp
import java.util.Date

/**
 * User model representing a user in the Thrift marketplace.
 * Stored in Firestore under the 'users' collection.
 */
data class User(
    val uid: String = "",
    val email: String = "",
    val displayName: String = "",
    val profileImageUrl: String? = null,
    val bio: String? = null,
    val rating: Double = 0.0,
    val totalReviews: Int = 0,
    @ServerTimestamp
    val createdAt: Date? = null,
    @ServerTimestamp
    val updatedAt: Date? = null
)

