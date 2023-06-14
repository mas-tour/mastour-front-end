package com.mastour.mastour.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ImgurApiService {
    @Multipart
    @Headers("Authorization: Client-ID 3bc735ced126e46")
    @POST("/3/image")
    suspend fun uploadFile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody? = null
    ): Response<ImgurResponse>

    // TODO: This function currently unused.
    @Headers("Authorization: Client-ID 3bc735ced126e46")
    @POST("/3/image")
    suspend fun uploadFile(
        @Body requestBody: RequestBody
    ): Response<ImgurResponse>
}