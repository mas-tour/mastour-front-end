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