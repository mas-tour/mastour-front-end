package com.mastour.mastour.data.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.mastour.mastour.data.local.UserData
import com.mastour.mastour.data.pagingsource.GuidePagingSource
import com.mastour.mastour.data.pagingsource.HistoryPagingSource
import com.mastour.mastour.data.preferences.SessionPreferences
import com.mastour.mastour.data.remote.*
import com.mastour.mastour.util.AuthUiState
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
import retrofit2.Response
import javax.inject.Inject

class Repository @Inject constructor(
    private val preferences: SessionPreferences,
    private val masTourApiService: MasTourApiService,
    private val imgurApiService: ImgurApiService,
    @ApplicationContext private val context: Context
) {
    fun getGuides(
        bearer: String,
        query: String = "",
        cityId: String? = null,
        categoryId: String? = null
    ) = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = {
            GuidePagingSource(
                masTourApiService,
                bearer,
                query = query,
                cityId = cityId,
                categoryId = categoryId
            )
        }
    ).flow

    fun getHistory(bearer: String) = Pager(
        config = PagingConfig(
            pageSize = 20,
        ),
        pagingSourceFactory = { HistoryPagingSource(masTourApiService, bearer) }
    ).flow

    fun getUserExist(): Flow<Boolean> {
        return preferences.getUserExist()
    }

    fun getUserToken(): Flow<String> {
        return preferences.getUserToken()
    }


    suspend fun deleteSession() {
        preferences.deleteSession()
    }

    fun login(email: String, password: String): Flow<AuthUiState<LoginResponses>> {
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responseLogin = masTourApiService.login(requestBody)
                responseLogin.data?.token?.let { preferences.startSession(true, it) }
                emit(AuthUiState.Success(responseLogin))
            } catch (e: Exception) {
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun register(
        email: String,
        username: String,
        name: String,
        password: String,
        gender: String,
        picture: String
    ): Flow<AuthUiState<RegisterResponses>> {
        val jsonObject = JSONObject()
        jsonObject.put("username", username)
        jsonObject.put("email", email)
        jsonObject.put("password", password)
        jsonObject.put("name", name)

        // The api requires these property, dummy for now; Report later.
        jsonObject.put("phone_number", "")
        jsonObject.put("gender", gender)
        jsonObject.put("birth_date", 0)

        jsonObject.put("picture", picture)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(AuthUiState.Idle)
                emit(AuthUiState.Load)
                val responseRegister = masTourApiService.register(requestBody)
                emit(AuthUiState.Success(responseRegister))
            } catch (e: Exception) {
                emit(AuthUiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun uploadImage(image: Uri): Result<String> {
        return try {
            val response: Response<ImgurResponse>
            if (image == Uri.EMPTY) {
                val link = "https://i.imgur.com//AwpoAoG.png"
                val jsonObject = JSONObject()
                jsonObject.put("image", link)

                Log.d("UploadImage", jsonObject.toString())

                val requestBody =
                    jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

                response = imgurApiService.uploadFile(
                    requestBody
                )
            } else {
                val file = uriToFile(image, context)
                val filePart =
                    MultipartBody.Part.createFormData("image", null, file.asRequestBody())

                response = imgurApiService.uploadFile(
                    filePart
                )
            }

            if (response.isSuccessful) {
                val link = response.body()?.upload?.link ?: ""
                Log.d("ImgurAPI", link)
                Result.success(link)
            } else {
                Log.d("ImgurAPI", "Error")
                Result.failure(Exception("Unknown network Exception."))
            }

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getProfile(bearer: String): Flow<UiState<ProfileResponse>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseLogin = masTourApiService.getProfile(bearer)
                emit(UiState.Success(responseLogin))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun updateProfile(userData: UserData, bearer: String): Flow<UiState<ProfileResponse>> {
        val jsonObject = JSONObject()
        jsonObject.put("username", userData.username)
        jsonObject.put("email", userData.email)
        jsonObject.put("name", userData.name)
        jsonObject.put("phone_number", userData.phoneNumber)
        jsonObject.put("gender", userData.gender)
        jsonObject.put("birth_date", userData.birthDate)
        jsonObject.put("picture", userData.picture)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseProfile = masTourApiService.putProfile("Bearer $bearer", requestBody)
                emit(UiState.Success(responseProfile))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    // TODO: Maybe not UIState?
    fun bookGuide(
        startDate: Long,
        endDate: Long,
        bearer: String,
        id: String
    ): Flow<UiState<BookGuidesResponse>> {
        val jsonObject = JSONObject()
        jsonObject.put("start_date", startDate)
        jsonObject.put("end_date", endDate)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        Log.d("Post", jsonObject.toString())
        return flow {
            try {
                Log.d("Post", "Try, token: $bearer")
                emit(UiState.Loading)
                val responseBooking = masTourApiService.bookGuide("Bearer $bearer", requestBody, id)
                emit(UiState.Success(responseBooking))
                Log.d("Post", responseBooking.toString())
            } catch (e: Exception) {
                Log.d("Post", "Fail")
                Log.d("Post", e.toString())
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
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
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getSurveyResults(
        cityId: String,
        pickedCategories: List<Int>,
        token: String
    ): Flow<UiState<ResponseSurveyResults>> {
        val jsonObject = JSONObject()
        val jsonArray = JSONArray(pickedCategories)
        jsonObject.put("city_id", cityId)
        jsonObject.put("categories", jsonArray)

        val requestBody =
            jsonObject.toString().toRequestBody("application/json".toMediaTypeOrNull())

        return flow {
            try {
                emit(UiState.Loading)
                val responseSurveyResults =
                    masTourApiService.getSurvey("Bearer $token", requestBody)
                emit(UiState.Success(responseSurveyResults))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }
    }


    fun detailedGuides(id: String, token: String): Flow<UiState<DetailGuidesResponse>> {
        return flow {
            try {
                emit(UiState.Loading)
                val responseDetailGuide = masTourApiService.getDetailedGuide("Bearer $token", id)
                emit(UiState.Success(responseDetailGuide))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getCategories(): Flow<UiState<CategoriesHelper>> {
        return flow {
            try {
                emit(UiState.Loading)
                val citiesResponse = masTourApiService.getCities()
                val specResponse = masTourApiService.getCategories()
                val responseCategoriesHelper = CategoriesHelper(citiesResponse, specResponse)
                emit(UiState.Success(responseCategoriesHelper))
            } catch (e: Exception) {
                emit(UiState.Failure(e))
            }
        }.flowOn(Dispatchers.IO)
    }
}