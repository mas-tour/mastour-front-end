package com.mastour.mastour.data.remote

import com.google.gson.annotations.SerializedName

class ImgurResponse(

    @field:SerializedName("data")
    val upload: Upload,

    @field:SerializedName("success")
    val success: Boolean
)

class Upload(
    @field:SerializedName("link")
    val link: String? = null,

    @field:SerializedName("id")
    val id: String? = null,

    @field:SerializedName("height")
    val height: Int? = null,

    @field:SerializedName("size")
    val size: Int? = null,

    @field:SerializedName("width")
    val width: Int? = null,

    @field:SerializedName("name")
    val name: String? = null
)
