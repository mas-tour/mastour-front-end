package com.mastour.mastour.data.remote

import com.google.gson.annotations.SerializedName

data class LoginResponses(
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("data")
	val data: Login? = null
)

data class Login(

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("token")
	val token: String? = null
)

data class RegisterResponses(
	@field:SerializedName("message")
	val message: String? = null,
	@field:SerializedName("data")
	val data: Register? = null
)

data class Register(
	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("email")
	val email: String? = null,
)

data class SurveyResponse(

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("gender")
	val gender: String,

	@field:SerializedName("birth_date")
	val birthDate: Int,

	@field:SerializedName("answers")
	val answers: List<Int>,

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("password")
	val password: String,

	@field:SerializedName("personality")
	val personality: Int,

	@field:SerializedName("updated_at")
	val updatedAt: Long,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("phone_number")
	val phoneNumber: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String
)

data class ResponseGuides(

	@field:SerializedName("data")
	val data: List<DataGuides>
)

data class DataGuides(

	@field:SerializedName("city")
	val city: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("detail_picture")
	val detailPicture: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("price_per_day")
	val pricePerDay: Long,

	@field:SerializedName("picture")
	val picture: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem>,
)

data class CategoriesItem(

	@field:SerializedName("updated_at")
	val updatedAt: Long,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("created_at")
	val createdAt: Long,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("slug")
	val slug: String
)

data class DetailGuidesResponse(

	@field:SerializedName("data")
	val data: DataDetailGuides? = null
)


data class TopPlacesItem(

	@field:SerializedName("updated_at")
	val updatedAt: Long? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Long? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("picture")
	val picture: String? = null
)

data class DataDetailGuides(

	@field:SerializedName("gender")
	val gender: String? = null,

	@field:SerializedName("city")
	val city: String? = null,

	@field:SerializedName("birth_date")
	val birthDate: Long? = null,

	@field:SerializedName("answers")
	val answers: List<Int?>? = null,

	@field:SerializedName("detail_picture")
	val detailPicture: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("created_at")
	val createdAt: Long? = null,

	@field:SerializedName("picture")
	val picture: String? = null,

	@field:SerializedName("top_places")
	val topPlaces: List<TopPlacesItem>,

	@field:SerializedName("personality")
	val personality: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Long? = null,

	@field:SerializedName("user_id")
	val userId: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("phone_number")
	val phoneNumber: String? = null,

	@field:SerializedName("id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("price_per_day")
	val pricePerDay: Long? = null,

	@field:SerializedName("email")
	val email: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("city_id")
	val cityId: String? = null
)