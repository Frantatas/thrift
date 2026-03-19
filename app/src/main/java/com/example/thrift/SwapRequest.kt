package com.example.thrift

data class SwapRequest(
    val requestedItemName: String,
    val requestedItemSize: String,
    val requestedItemCondition: String,
    val requestedItemImage: Int,
    val offeredItemName: String,
    val offeredItemSize: String,
    val offeredItemCondition: String,
    val offeredItemImage: Int,
    val message: String,
    val status: String
)