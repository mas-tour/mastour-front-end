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