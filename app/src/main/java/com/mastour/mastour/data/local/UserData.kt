package com.mastour.mastour.data.local

// TODO: Possibly, temporary... Should I implement datastore to store data?
data class UserData(
    var username: String? = null,
    var email: String? = null,
    var name: String? = null,
    var phoneNumber: String? = null,
    var gender: String? = null,
    var birthDate: Long? = null,
    var picture: String? = null
)