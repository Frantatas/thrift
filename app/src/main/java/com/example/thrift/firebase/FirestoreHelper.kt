package com.example.thrift.firebase

import com.example.thrift.data.models.Item
import com.example.thrift.data.models.SwapItem
import com.example.thrift.data.models.SwapRequest
import com.example.thrift.data.models.Transaction
import com.example.thrift.data.models.User
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

/**
 * FirestoreHelper provides a singleton access point to Cloud Firestore.
 * Encapsulates all Firestore database operations.
 */
class FirestoreHelper {
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    companion object {
        const val USERS_COLLECTION = "users"
        const val ITEMS_COLLECTION = "items"
        const val TRANSACTIONS_COLLECTION = "transactions"
        const val SWAP_ITEMS_COLLECTION = "swapItems"
        const val SWAP_REQUESTS_COLLECTION = "swapRequests"
    }

    // ==================== USER OPERATIONS ====================

    /**
     * Create or update a user profile.
     */
    suspend fun setUserProfile(user: User): Boolean {
        return try {
            db.collection(USERS_COLLECTION).document(user.uid).set(user).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get a user profile by UID.
     */
    suspend fun getUserProfile(uid: String): User? {
        return try {
            val doc = db.collection(USERS_COLLECTION).document(uid).get().await()
            doc.toObject(User::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Update user profile fields.
     */
    suspend fun updateUserProfile(uid: String, updates: Map<String, Any>): Boolean {
        return try {
            db.collection(USERS_COLLECTION).document(uid).update(updates).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // ==================== ITEM OPERATIONS ====================

    /**
     * Create a new item listing.
     */
    suspend fun createItem(item: Item): Boolean {
        return try {
            db.collection(ITEMS_COLLECTION).document(item.itemId).set(item).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get all available items.
     */
    suspend fun getAllItems(): List<Item> {
        return try {
            val snapshot = db.collection(ITEMS_COLLECTION)
                .whereEqualTo("isAvailable", true)
                .get()
                .await()
            snapshot.toObjects(Item::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get items by seller ID.
     */
    suspend fun getItemsBySeller(sellerId: String): List<Item> {
        return try {
            val snapshot = db.collection(ITEMS_COLLECTION)
                .whereEqualTo("sellerId", sellerId)
                .get()
                .await()
            snapshot.toObjects(Item::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get a single item by ID.
     */
    suspend fun getItemById(itemId: String): Item? {
        return try {
            val doc = db.collection(ITEMS_COLLECTION).document(itemId).get().await()
            doc.toObject(Item::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Update an item.
     */
    suspend fun updateItem(itemId: String, updates: Map<String, Any>): Boolean {
        return try {
            db.collection(ITEMS_COLLECTION).document(itemId).update(updates).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Delete an item.
     */
    suspend fun deleteItem(itemId: String): Boolean {
        return try {
            db.collection(ITEMS_COLLECTION).document(itemId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // ==================== TRANSACTION OPERATIONS ====================

    /**
     * Create a new transaction (purchase).
     */
    suspend fun createTransaction(transaction: Transaction): Boolean {
        return try {
            db.collection(TRANSACTIONS_COLLECTION).document(transaction.transactionId).set(transaction).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get all transactions for a buyer.
     */
    suspend fun getBuyerTransactions(buyerId: String): List<Transaction> {
        return try {
            val snapshot = db.collection(TRANSACTIONS_COLLECTION)
                .whereEqualTo("buyerId", buyerId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(Transaction::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get all transactions for a seller.
     */
    suspend fun getSellerTransactions(sellerId: String): List<Transaction> {
        return try {
            val snapshot = db.collection(TRANSACTIONS_COLLECTION)
                .whereEqualTo("sellerId", sellerId)
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()
            snapshot.toObjects(Transaction::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Update a transaction status.
     */
    suspend fun updateTransactionStatus(transactionId: String, status: String): Boolean {
        return try {
            db.collection(TRANSACTIONS_COLLECTION).document(transactionId)
                .update(mapOf("status" to status)).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // ==================== SWAP ITEM OPERATIONS ====================

    /**
     * Create a new swap item listing.
     */
    suspend fun createSwapItem(swapItem: SwapItem): Boolean {
        return try {
            db.collection(SWAP_ITEMS_COLLECTION).document(swapItem.swapItemId).set(swapItem).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get all available swap items.
     */
    suspend fun getAllSwapItems(): List<SwapItem> {
        return try {
            val snapshot = db.collection(SWAP_ITEMS_COLLECTION)
                .whereEqualTo("isAvailable", true)
                .get()
                .await()
            snapshot.toObjects(SwapItem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get swap items by user ID.
     */
    suspend fun getSwapItemsByUser(userId: String): List<SwapItem> {
        return try {
            val snapshot = db.collection(SWAP_ITEMS_COLLECTION)
                .whereEqualTo("userId", userId)
                .get()
                .await()
            snapshot.toObjects(SwapItem::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get a single swap item by ID.
     */
    suspend fun getSwapItemById(swapItemId: String): SwapItem? {
        return try {
            val doc = db.collection(SWAP_ITEMS_COLLECTION).document(swapItemId).get().await()
            doc.toObject(SwapItem::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Update a swap item.
     */
    suspend fun updateSwapItem(swapItemId: String, updates: Map<String, Any>): Boolean {
        return try {
            db.collection(SWAP_ITEMS_COLLECTION).document(swapItemId).update(updates).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Delete a swap item.
     */
    suspend fun deleteSwapItem(swapItemId: String): Boolean {
        return try {
            db.collection(SWAP_ITEMS_COLLECTION).document(swapItemId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }

    // ==================== SWAP REQUEST OPERATIONS ====================

    /**
     * Create a new swap request.
     */
    suspend fun createSwapRequest(swapRequest: SwapRequest): Boolean {
        return try {
            db.collection(SWAP_REQUESTS_COLLECTION).document(swapRequest.requestId).set(swapRequest).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get all swap requests for a user (both sent and received).
     */
    suspend fun getUserSwapRequests(userId: String): List<SwapRequest> {
        return try {
            val snapshot = db.collection(SWAP_REQUESTS_COLLECTION)
                .whereEqualTo("requesterId", userId)
                .get()
                .await()
            snapshot.toObjects(SwapRequest::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get swap requests received by a user.
     */
    suspend fun getReceivedSwapRequests(userId: String): List<SwapRequest> {
        return try {
            val snapshot = db.collection(SWAP_REQUESTS_COLLECTION)
                .whereEqualTo("recipientId", userId)
                .get()
                .await()
            snapshot.toObjects(SwapRequest::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    /**
     * Get a single swap request by ID.
     */
    suspend fun getSwapRequestById(requestId: String): SwapRequest? {
        return try {
            val doc = db.collection(SWAP_REQUESTS_COLLECTION).document(requestId).get().await()
            doc.toObject(SwapRequest::class.java)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Update a swap request status.
     */
    suspend fun updateSwapRequestStatus(requestId: String, status: String): Boolean {
        return try {
            db.collection(SWAP_REQUESTS_COLLECTION).document(requestId)
                .update(mapOf("status" to status)).await()
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Delete a swap request.
     */
    suspend fun deleteSwapRequest(requestId: String): Boolean {
        return try {
            db.collection(SWAP_REQUESTS_COLLECTION).document(requestId).delete().await()
            true
        } catch (e: Exception) {
            false
        }
    }
}

object FirestoreHelperInstance {
    val instance: FirestoreHelper by lazy { FirestoreHelper() }
}

