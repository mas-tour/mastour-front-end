package com.mastour.mastour.data.repository

import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.remote.MasTourApiService
import com.mastour.mastour.util.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import javax.inject.Inject

class Repository @Inject constructor(
    private val masTourApiService: MasTourApiService
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
}