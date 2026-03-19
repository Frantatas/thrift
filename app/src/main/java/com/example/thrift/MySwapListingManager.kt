package com.example.thrift

object MySwapListingManager {
    val listings = mutableListOf(
        MySwapListing(
            itemName = "Red Cropped Polo",
            itemSize = "Small",
            itemCondition = "9/10",
            itemImage = R.drawable.red_cropped_polo,
            status = "Available"
        ),
        MySwapListing(
            itemName = "Low Rise Jeans",
            itemSize = "Small",
            itemCondition = "8/10",
            itemImage = R.drawable.low_rise_jeans,
            status = "In Negotiation"
        ),
        MySwapListing(
            itemName = "NY Shirt",
            itemSize = "Medium",
            itemCondition = "9/10",
            itemImage = R.drawable.ny_shirt,
            status = "Available"
        )
    )
}