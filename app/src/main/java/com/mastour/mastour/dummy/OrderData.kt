package com.mastour.mastour.dummy

import com.mastour.mastour.R

data class OrderDummy(
    val id: String,
    val guideName: String,
    val location: String,
    val duration: Int,
    val status: String,
    val price: Int,
    val photoUrl: Int
)

object OrderData {
    val orders = listOf(
        OrderDummy(
            "1",
            "M. Thariq Dorong",
            "Bandung",
            8,
            "complete",
            200000,
            R.drawable.dummy_user
        ),
        OrderDummy(
            "2",
            "Jeremy Elbertson",
            "Makassar",
            10,
            "ongoing",
            223000,
            R.drawable.dummy_user
        ),
        OrderDummy(
            "3",
            "Charlie Watkinson",
            "Makassar",
            10,
            "ongoing",
            250000,
            R.drawable.dummy_user
        ),
        OrderDummy(
            "4",
            "Bagus Wijaya",
            "Bandung",
            30,
            "complete",
            200000,
            R.drawable.dummy_user
        )
    )
}