package com.mastour.mastour.data.repository

import android.graphics.Bitmap
import com.mastour.mastour.data.remote.ImgurApiService
import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.remote.MasTourApiService
import com.mastour.mastour.data.remote.RegisterResponses
import com.mastour.mastour.data.remote.Upload
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.convertBitmapToBase64
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val masTourApiService: MasTourApiService,
    private val imgurApiService: ImgurApiService
) {

    fun login(email: String, password: String) : Flow<UiState<LoginResponses>> {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseLogin = masTourApiService.login(requestBody)
                emit(UiState.Success(responseLogin))
            }
            catch (e : Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun register(
        email: String,
        username: String,
        name: String,
        password: String,
        picture: String
    ) : Flow<UiState<RegisterResponses>> {
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("name", name)
        jsonObject.put("picture", picture)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseRegister = masTourApiService.register(requestBody)
                emit(UiState.Success(responseRegister))
            }
            catch (e : Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadImage(image: Bitmap): Result<Upload> {
        return try {
            val link = convertBitmapToBase64(image)

            val requestBody = link.toRequestBody("text/plain".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("image", null, requestBody)

            val response = imgurApiService.uploadFile(filePart)

            if(response.isSuccessful) {
                Result.success(response.body()!!.upload)
            } else {
                Result.failure(Exception("Unknown network Exception."))
            }

        } catch (e: Exception){
            Result.failure(e)
        }
    }
}