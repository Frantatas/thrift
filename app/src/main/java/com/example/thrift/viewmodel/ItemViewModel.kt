package com.example.thrift.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thrift.data.models.Item
import com.example.thrift.repository.FirestoreRepository
import kotlinx.coroutines.launch

/**
 * ItemViewModel manages item listing and browsing operations.
 * Uses LiveData to observe item data changes in the UI.
 */
class ItemViewModel(
    private val firestoreRepository: FirestoreRepository = FirestoreRepository()
) : ViewModel() {

    private val _allItems = MutableLiveData<List<Item>>(emptyList())
    val allItems: LiveData<List<Item>> = _allItems

    private val _userItems = MutableLiveData<List<Item>>(emptyList())
    val userItems: LiveData<List<Item>> = _userItems

    private val _selectedItem = MutableLiveData<Item?>()
    val selectedItem: LiveData<Item?> = _selectedItem

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    fun loadAllItems() {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getAllItems()
            result.onSuccess { items ->
                _allItems.value = items
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load items"
                _isLoading.value = false
            }
        }
    }

    fun loadUserItems(userId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.getItemsBySeller(userId)
            result.onSuccess { items ->
                _userItems.value = items
                _errorMessage.value = null
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load user items"
                _isLoading.value = false
            }
        }
    }

    fun loadItemDetail(itemId: String) {
        viewModelScope.launch {
            val result = firestoreRepository.getItemById(itemId)
            result.onSuccess { item ->
                _selectedItem.value = item
                _errorMessage.value = null
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to load item"
            }
        }
    }

    fun createItem(item: Item) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.createItem(item)
            result.onSuccess {
                _errorMessage.value = "Item created successfully"
                loadAllItems()
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to create item"
                _isLoading.value = false
            }
        }
    }

    fun deleteItem(itemId: String) {
        _isLoading.value = true
        viewModelScope.launch {
            val result = firestoreRepository.deleteItem(itemId)
            result.onSuccess {
                _errorMessage.value = "Item deleted successfully"
                loadAllItems()
                _isLoading.value = false
            }
            result.onFailure { exception ->
                _errorMessage.value = exception.message ?: "Failed to delete item"
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}

