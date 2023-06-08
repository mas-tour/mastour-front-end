package com.mastour.mastour.data.remote

import okhttp3.RequestBody
import retrofit2.http.*

interface MasTourApiService {
    @POST("auth/sign-in")
    suspend fun login(@Body requestBody: RequestBody) : LoginResponses

    @POST("auth/sign-up")
    suspend fun register(@Body requestBody: RequestBody) : RegisterResponses

    @POST("matchmaking/survey")
    suspend fun submitSurvey(
        @Header("Authorization") token: String,
        @Body requestBody: RequestBody
    ) : SurveyResponse

    @GET("guides")
    suspend fun getGuides(
        @Header("Authorization") bearer: String,
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("search_by") searchBy: String = "users.name",
        @Query("search_query") query: String = ""
    ) : ResponseGuides

    @GET("guides/{id}")
    suspend fun getDetailedGuide(
        @Header("Authorization") bearer: String,
        @Path("id") id: String
    ) : DetailGuidesResponse

}