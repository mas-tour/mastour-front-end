package com.mastour.mastour.data.remote

import okhttp3.RequestBody
import retrofit2.http.*

interface MasTourApiService {
    @POST("auth/sign-in")
    suspend fun login(@Body requestBody: RequestBody) : LoginResponses

    @POST("auth/sign-up")
    suspend fun register(@Body requestBody: RequestBody) : RegisterResponses

    @GET("guides")
    suspend fun getGuides(
        @Header("Authorization") bearer: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ) : ResponseGuides
}