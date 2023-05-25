package com.mastour.mastour.dummy

import com.mastour.mastour.R

//Dummy Data
data class Guide(
    val id: String,
    val name: String,
    val photoUrl: Int,
    val place: String,
    val specialization: String,
    val price: Int
)

// Dummy Data
object GuideData {
    val guides = listOf(
        Guide(
            "1",
            "Bagas Wijoyo",
            R.drawable.dummy_user,
            "Makassar",
            "Culinary",
            200000
        ),
        Guide(
            "2",
            "Andra Winata",
            R.drawable.dummy_user,
            "Bandung",
            "Sight Seeing",
            150000
        ),
        Guide(
            "3",
            "Mekdi Dago",
            R.drawable.dummy_user,
            "Bandung",
            "Culinary",
            150000
        ),
        Guide(
            "4",
            "Nina",
            R.drawable.dummy_user,
            "Makassar",
            "Horror Attraction",
            100000
        ),
        Guide(
            "5",
            "Siti Wodjonegoro",
            R.drawable.dummy_user,
            "Bandung",
            "Horror Attraction",
            2000000
        )
    )
}