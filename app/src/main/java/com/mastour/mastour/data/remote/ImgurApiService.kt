package com.mastour.mastour.data.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ImgurApiService {
    @Multipart
    @Headers("Authorization: Client-ID 3bc735ced126e46")
    @POST("/3/upload")
    suspend fun uploadFile(
        @Part image: MultipartBody.Part?,
        @Part("name") name: RequestBody? = null
    ): Response<ImgurResponse>
}

object ApiKeys {
    const val CLIENT_ID = "3bc735ced126e46"
}