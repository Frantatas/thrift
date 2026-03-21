package com.example.thrift.repository

import com.example.thrift.data.models.Item
import com.example.thrift.data.models.SwapItem
import com.example.thrift.data.models.SwapRequest
import com.example.thrift.data.models.Transaction
import com.example.thrift.firebase.FirestoreHelper

/**
 * FirestoreRepository abstracts Firestore database operations and provides
 * a clean interface for the presentation layer to interact with the database.
 * This ensures separation of concerns and makes the code testable.
 */
class FirestoreRepository(
    private val firestoreHelper: FirestoreHelper = FirestoreHelper()
) {

    // ==================== ITEM OPERATIONS ====================

    suspend fun createItem(item: Item): Result<Unit> {
        return try {
            val success = firestoreHelper.createItem(item)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to create item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllItems(): Result<List<Item>> {
        return try {
            val items = firestoreHelper.getAllItems()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getItemsBySeller(sellerId: String): Result<List<Item>> {
        return try {
            val items = firestoreHelper.getItemsBySeller(sellerId)
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getItemById(itemId: String): Result<Item> {
        return try {
            val item = firestoreHelper.getItemById(itemId)
                ?: return Result.failure(Exception("Item not found"))
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateItem(itemId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            val success = firestoreHelper.updateItem(itemId, updates)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to update item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteItem(itemId: String): Result<Unit> {
        return try {
            val success = firestoreHelper.deleteItem(itemId)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to delete item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ==================== TRANSACTION OPERATIONS ====================

    suspend fun createTransaction(transaction: Transaction): Result<Unit> {
        return try {
            val success = firestoreHelper.createTransaction(transaction)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to create transaction"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getBuyerTransactions(buyerId: String): Result<List<Transaction>> {
        return try {
            val transactions = firestoreHelper.getBuyerTransactions(buyerId)
            Result.success(transactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSellerTransactions(sellerId: String): Result<List<Transaction>> {
        return try {
            val transactions = firestoreHelper.getSellerTransactions(sellerId)
            Result.success(transactions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateTransactionStatus(transactionId: String, status: String): Result<Unit> {
        return try {
            val success = firestoreHelper.updateTransactionStatus(transactionId, status)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to update transaction"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ==================== SWAP ITEM OPERATIONS ====================

    suspend fun createSwapItem(swapItem: SwapItem): Result<Unit> {
        return try {
            val success = firestoreHelper.createSwapItem(swapItem)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to create swap item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAllSwapItems(): Result<List<SwapItem>> {
        return try {
            val items = firestoreHelper.getAllSwapItems()
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSwapItemsByUser(userId: String): Result<List<SwapItem>> {
        return try {
            val items = firestoreHelper.getSwapItemsByUser(userId)
            Result.success(items)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSwapItemById(swapItemId: String): Result<SwapItem> {
        return try {
            val item = firestoreHelper.getSwapItemById(swapItemId)
                ?: return Result.failure(Exception("Swap item not found"))
            Result.success(item)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateSwapItem(swapItemId: String, updates: Map<String, Any>): Result<Unit> {
        return try {
            val success = firestoreHelper.updateSwapItem(swapItemId, updates)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to update swap item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteSwapItem(swapItemId: String): Result<Unit> {
        return try {
            val success = firestoreHelper.deleteSwapItem(swapItemId)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to delete swap item"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // ==================== SWAP REQUEST OPERATIONS ====================

    suspend fun createSwapRequest(swapRequest: SwapRequest): Result<Unit> {
        return try {
            val success = firestoreHelper.createSwapRequest(swapRequest)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to create swap request"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUserSwapRequests(userId: String): Result<List<SwapRequest>> {
        return try {
            val requests = firestoreHelper.getUserSwapRequests(userId)
            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getReceivedSwapRequests(userId: String): Result<List<SwapRequest>> {
        return try {
            val requests = firestoreHelper.getReceivedSwapRequests(userId)
            Result.success(requests)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getSwapRequestById(requestId: String): Result<SwapRequest> {
        return try {
            val request = firestoreHelper.getSwapRequestById(requestId)
                ?: return Result.failure(Exception("Swap request not found"))
            Result.success(request)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun updateSwapRequestStatus(requestId: String, status: String): Result<Unit> {
        return try {
            val success = firestoreHelper.updateSwapRequestStatus(requestId, status)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to update swap request"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun deleteSwapRequest(requestId: String): Result<Unit> {
        return try {
            val success = firestoreHelper.deleteSwapRequest(requestId)
            if (success) Result.success(Unit) else Result.failure(Exception("Failed to delete swap request"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}

object FirestoreRepositoryInstance {
    val instance: FirestoreRepository by lazy { FirestoreRepository() }
}

