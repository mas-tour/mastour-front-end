package com.mastour.mastour.dummy

import com.mastour.mastour.R

data class CategoryData(
    val id: String,
    val name: String,
    val photoUrl: String
)

//Dummy Data (To Be Deleted)
data class CategoryData2(
    val id: String,
    val name: String,
    val photoUrl: Int
)

//Dummy Data (To Be Deleted)
object CategoryDatas2{
    val category = listOf(
        CategoryData2(
            "1",
            "Culinary",
            R.drawable.culinary
        ),
        CategoryData2(
            "2",
            "Culture",
            R.drawable.culture
        ),
        CategoryData2(
            "3",
            "Extreme",
            R.drawable.extreme
        ),
        CategoryData2(
            "4",
            "Family Trip",
            R.drawable.family_trip
        ),
        CategoryData2(
            "5",
            "Historical",
            R.drawable.historical
        ),
        CategoryData2(
            "6",
            "Shopping",
            R.drawable.shopping
        ),
        CategoryData2(
            "7",
            "Sightseeing",
            R.drawable.sightseeing
        )
    )

    val place = listOf(
        CategoryData2(
            "1",
            "Makassar",
            R.drawable.makassar
        ),
        CategoryData2(
            "2",
            "Bandung",
            R.drawable.bandung
        )
    )

    val top = listOf(
        CategoryData2(
            "1",
            "Borobudur",
            R.drawable.borobudur
        ),
        CategoryData2(
            "2",
            "Borobudur",
            R.drawable.borobudur
        ),
        CategoryData2(
            "2",
            "Borobudur",
            R.drawable.borobudur
        ),
    )
}