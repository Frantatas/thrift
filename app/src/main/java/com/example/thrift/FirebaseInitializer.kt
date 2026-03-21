package com.example.thrift

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.Timestamp
import kotlinx.coroutines.tasks.await

/**
 * Initializes Firestore collections on first app run.
 * Creates collections with default documents if they don't exist.
 */
object FirebaseInitializer {

    suspend fun initializeFirestore(context: Context) {
        val db = FirebaseFirestore.getInstance()
        val prefs = context.getSharedPreferences("firebase_init", Context.MODE_PRIVATE)
        val isInitialized = prefs.getBoolean("collections_initialized", false)

        if (!isInitialized) {
            try {
                // Create users collection
                createUsersCollection(db)

                // Create items collection
                createItemsCollection(db)

                // Create swapItems collection
                createSwapItemsCollection(db)

                // Create swapRequests collection
                createSwapRequestsCollection(db)

                // Create transactions collection
                createTransactionsCollection(db)

                // Mark initialization as complete
                prefs.edit().putBoolean("collections_initialized", true).apply()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun createUsersCollection(db: FirebaseFirestore) {
        try {
            // Create a default user document to initialize the collection
            val defaultUser = mapOf(
                "uid" to "default_user",
                "email" to "default@thrift.app",
                "displayName" to "Thrift User",
                "profileImageUrl" to null,
                "bio" to "Welcome to Thrift!",
                "rating" to 0.0,
                "totalReviews" to 0,
                "createdAt" to Timestamp.now(),
                "updatedAt" to Timestamp.now()
            )

            // Check if default user exists
            val doc = db.collection("users").document("default_user").get().await()
            if (!doc.exists()) {
                db.collection("users").document("default_user").set(defaultUser).await()
                // Delete the default document after creating collection
                db.collection("users").document("default_user").delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun createItemsCollection(db: FirebaseFirestore) {
        try {
            // Create a default item document to initialize the collection
            val defaultItem = mapOf(
                "itemId" to "default_item",
                "sellerId" to "default_seller",
                "sellerName" to "Default Seller",
                "title" to "Sample Item",
                "description" to "This is a sample item",
                "price" to 0.0,
                "condition" to "Good",
                "category" to "Other",
                "size" to "One Size",
                "color" to "Unknown",
                "imageUrls" to emptyList<String>(),
                "isAvailable" to true,
                "createdAt" to Timestamp.now(),
                "updatedAt" to Timestamp.now()
            )

            // Check if default item exists
            val doc = db.collection("items").document("default_item").get().await()
            if (!doc.exists()) {
                db.collection("items").document("default_item").set(defaultItem).await()
                // Delete the default document after creating collection
                db.collection("items").document("default_item").delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun createSwapItemsCollection(db: FirebaseFirestore) {
        try {
            // Create a default swap item document to initialize the collection
            val defaultSwapItem = mapOf(
                "swapItemId" to "default_swap_item",
                "userId" to "default_user",
                "userName" to "Default User",
                "title" to "Sample Swap Item",
                "description" to "This is a sample swap item",
                "condition" to "Good",
                "category" to "Other",
                "size" to "One Size",
                "color" to "Unknown",
                "imageUrls" to emptyList<String>(),
                "isAvailable" to true,
                "createdAt" to Timestamp.now(),
                "updatedAt" to Timestamp.now()
            )

            // Check if default swap item exists
            val doc = db.collection("swapItems").document("default_swap_item").get().await()
            if (!doc.exists()) {
                db.collection("swapItems").document("default_swap_item").set(defaultSwapItem).await()
                // Delete the default document after creating collection
                db.collection("swapItems").document("default_swap_item").delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun createSwapRequestsCollection(db: FirebaseFirestore) {
        try {
            // Create a default swap request document to initialize the collection
            val defaultSwapRequest = mapOf(
                "requestId" to "default_swap_request",
                "requesterId" to "default_requester",
                "requesterName" to "Default Requester",
                "recipientId" to "default_recipient",
                "recipientName" to "Default Recipient",
                "requesterItemId" to "default_item_1",
                "requesterItemTitle" to "Item 1",
                "requestedItemId" to "default_item_2",
                "requestedItemTitle" to "Item 2",
                "status" to "Pending",
                "message" to "I would like to swap",
                "createdAt" to Timestamp.now(),
                "respondedAt" to null
            )

            // Check if default swap request exists
            val doc = db.collection("swapRequests").document("default_swap_request").get().await()
            if (!doc.exists()) {
                db.collection("swapRequests").document("default_swap_request").set(defaultSwapRequest).await()
                // Delete the default document after creating collection
                db.collection("swapRequests").document("default_swap_request").delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun createTransactionsCollection(db: FirebaseFirestore) {
        try {
            // Create a default transaction document to initialize the collection
            val defaultTransaction = mapOf(
                "transactionId" to "default_transaction",
                "buyerId" to "default_buyer",
                "sellerId" to "default_seller",
                "itemId" to "default_item",
                "itemTitle" to "Sample Item",
                "quantity" to 1,
                "totalPrice" to 0.0,
                "status" to "Pending",
                "createdAt" to Timestamp.now(),
                "completedAt" to null
            )

            // Check if default transaction exists
            val doc = db.collection("transactions").document("default_transaction").get().await()
            if (!doc.exists()) {
                db.collection("transactions").document("default_transaction").set(defaultTransaction).await()
                // Delete the default document after creating collection
                db.collection("transactions").document("default_transaction").delete().await()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

