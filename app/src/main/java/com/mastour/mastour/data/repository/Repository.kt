package com.mastour.mastour.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mastour.mastour.data.pagingsource.GuidePagingSource
import com.mastour.mastour.data.preferences.SessionPreferences
import com.mastour.mastour.data.remote.*
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.uriToFile
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val preferences: SessionPreferences,
    private val masTourApiService: MasTourApiService,
    private val imgurApiService: ImgurApiService,
    @ApplicationContext private val context: Context
) {
    fun getGuides(bearer : String) = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {GuidePagingSource(masTourApiService, bearer)}
    ).flow

    fun getUserExist(): Flow<Boolean>{
        return preferences.getUserExist()
    }

    fun getUserToken(): Flow<String>{
        return preferences.getUserToken()
    }

    suspend fun deleteSession(){
        preferences.deleteSession()
    }


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
                responseLogin.data?.token?.let { preferences.startSession(true, it) }
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

        // The api requires these property, dummy for now; Report later.
        jsonObject.put("phone_number", "")
        jsonObject.put("gender", "male")
        jsonObject.put("birth_date", 0)

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

    suspend fun uploadImage(image: Uri): Result<String> {
        return try {
            val file = uriToFile(image, context)

            val filePart = MultipartBody.Part.
            createFormData("image", null, file!!.asRequestBody())

            val response = imgurApiService.uploadFile(
                filePart
            )

            if(response.isSuccessful){
                val link = response.body()?.upload?.link ?: ""
                Log.d("ImgurAPI", link)
                Result.success(link)
            } else {
                Log.d("ImgurAPI", "Error")
                Result.failure(Exception("Unknown network Exception."))
            }

        } catch (e: Exception){
            Result.failure(e)
        }
    }

    fun survey(answers: List<Int>, token: String): Flow<UiState<SurveyResponse>> {
        val jsonObject = JSONObject()
        val jsonArray = JSONArray(answers)
        jsonObject.put("answers", jsonArray)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseSurvey = masTourApiService.submitSurvey("Bearer $token", requestBody)
                emit(UiState.Success(responseSurvey))
            }
            catch (e : Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun detailedGuides(id: String, token: String) : Flow<UiState<DetailGuidesResponse>>{
        return flow {
            try {
                emit(UiState.Loading)
                val responseDetailGuide = masTourApiService.getDetailedGuide("Bearer $token", id)
                emit(UiState.Success(responseDetailGuide))
            }
            catch (e: Exception){
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}