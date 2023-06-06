package com.mastour.mastour.data.remote

import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.POST

interface MasTourApiService {
    @POST("auth/sign-in")
    suspend fun login(@Body requestBody: RequestBody) : LoginResponses

    @POST("auth/sign-up")
    suspend fun register(@Body requestBody: RequestBody) : RegisterResponses
}