package com.example.thrift

object SwapRequestManager {
    val swapRequests = mutableListOf<SwapRequest>()

    fun addRequest(request: SwapRequest) {
        swapRequests.add(request)
    }

    fun clearRequests() {
        swapRequests.clear()
    }
}