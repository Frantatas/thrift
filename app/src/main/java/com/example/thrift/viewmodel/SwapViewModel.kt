package com.example.thrift.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrift.data.models.SwapItem
import com.example.thrift.data.models.SwapRequest
import com.example.thrift.repository.FirestoreRepository
import kotlinx.coroutines.launch

/**
 * SwapViewModel manages swap item listings and swap requests.
 * Uses LiveData to observe swap data changes in the UI.
 */
class SwapViewModel(
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _allSwapItems = MutableLiveData<List<SwapItem>>(emptyList())
    val allSwapItems: LiveData<List<SwapItem>> = _allSwapItems

    private val _userSwapItems = MutableLiveData<List<SwapItem>>(emptyList())
    val userSwapItems: LiveData<List<SwapItem>> = _userSwapItems

    private val _userSwapRequests = MutableLiveData<List<SwapRequest>>(emptyList())
    val userSwapRequests: LiveData<List<SwapRequest>> = _userSwapRequests

    private val _receivedSwapRequests = MutableLiveData<List<SwapRequest>>(emptyList())
    val receivedSwapRequests: LiveData<List<SwapRequest>> = _receivedSwapRequests

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadAllSwapItems() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getAllSwapItems()
            result.onSuccess { items ->
                _allSwapItems.value = items
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load swap items"
                _isLoading.value = false
            }
        }
    }

    fun loadUserSwapItems(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getSwapItemsByUser(userId)
            result.onSuccess { items ->
                _userSwapItems.value = items
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load user swap items"
                _isLoading.value = false
            }
        }
    }

    fun createSwapItem(swapItem: SwapItem) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.createSwapItem(swapItem)
            result.onSuccess {
                _errorMessage.value = "Swap item created successfully"
                loadAllSwapItems()
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to create swap item"
                _isLoading.value = false
            }
        }
    }

    fun deleteSwapItem(swapItemId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.deleteSwapItem(swapItemId)
            result.onSuccess {
                _errorMessage.value = "Swap item deleted successfully"
                loadAllSwapItems()
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to delete swap item"
                _isLoading.value = false
            }
        }
    }

    fun loadUserSwapRequests(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getUserSwapRequests(userId)
            result.onSuccess { requests ->
                _userSwapRequests.value = requests
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load swap requests"
                _isLoading.value = false
            }
        }
    }

    fun loadReceivedSwapRequests(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getReceivedSwapRequests(userId)
            result.onSuccess { requests ->
                _receivedSwapRequests.value = requests
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load received requests"
                _isLoading.value = false
            }
        }
    }

    fun createSwapRequest(swapRequest: SwapRequest) {
        viewModelScope.launch {
            val result = firestoreRepository.createSwapRequest(swapRequest)
            result.onSuccess {
                _errorMessage.value = "Swap request sent successfully"
                loadUserSwapRequests(swapRequest.requesterId)
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to send swap request"
            }
        }
    }

    fun updateSwapRequestStatus(requestId: String, status: String, userId: String) {
        viewModelScope.launch {
            val result = firestoreRepository.updateSwapRequestStatus(requestId, status)
            result.onSuccess {
                _errorMessage.value = "Swap request updated successfully"
                loadReceivedSwapRequests(userId)
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to update swap request"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

