package com.mastour.mastour.data.remote

import com.google.gson.annotations.SerializedName

class ImgurResponse(

	@field:SerializedName("data")
	val upload: Upload,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("status")
	val status: Int
)

 class Upload(
	@field:SerializedName("in_most_viral")
	val inMostViral: Boolean? = null,

	@field:SerializedName("ad_type")
	val adType: Int? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("description")
	val description: Any? = null,

	@field:SerializedName("section")
	val section: Any? = null,

	@field:SerializedName("title")
	val title: Any? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("deletehash")
	val deletehash: String? = null,

	@field:SerializedName("datetime")
	val datetime: Int? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("in_gallery")
	val inGallery: Boolean? = null,

	@field:SerializedName("vote")
	val vote: Any? = null,

	@field:SerializedName("views")
	val views: Int? = null,

	@field:SerializedName("height")
	val height: Int? = null,

	@field:SerializedName("bandwidth")
	val bandwidth: Int? = null,

	@field:SerializedName("nsfw")
	val nsfw: Any? = null,

	@field:SerializedName("is_ad")
	val isAd: Boolean? = null,

	@field:SerializedName("ad_url")
	val adUrl: String? = null,

	@field:SerializedName("tags")
	val tags: List<Any?>? = null,

	@field:SerializedName("account_id")
	val accountId: Int? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("width")
	val width: Int? = null,

	@field:SerializedName("account_url")
	val accountUrl: Any? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("animated")
	val animated: Boolean? = null,

	@field:SerializedName("favorite")
	val favorite: Boolean? = null
)
