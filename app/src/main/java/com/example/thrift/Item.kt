package com.example.thrift

data class Item(
    val name: String,
    val price: String,
    val size: String,
    val condition: String,
    val description: String,
    val imageResId: Int,
    var quantity: Int = 1
)