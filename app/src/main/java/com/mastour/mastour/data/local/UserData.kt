package com.mastour.mastour.data.local

data class UserData(
    var username: String? = null,
    var email: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var gender: String? = null,
    var birthDate: Long? = null,
    var picture: String? = null
)