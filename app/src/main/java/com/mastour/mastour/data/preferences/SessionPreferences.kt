package com.mastour.mastour.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mastour.mastour.util.Constants
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionPreferences @Inject constructor(private val dataStore: DataStore<Preferences>) {
    private val userExist = booleanPreferencesKey(Constants.USER_EXIST)
    private val userToken = stringPreferencesKey(Constants.USER_TOKEN)

    fun getUserExist(): Flow<Boolean> {
        return dataStore.data.map {
            it[userExist] ?: false
        }
    }

    fun getUserToken(): Flow<String> {
        return dataStore.data.map {
            it[userToken] ?: ""
        }
    }

    suspend fun startSession(
        userChecked: Boolean,
        TokenChecked: String
    ) {
        dataStore.edit {
            it[userExist] = userChecked
            it[userToken] = TokenChecked
        }
    }

    suspend fun deleteSession() {
        dataStore.edit {
            it.clear()
        }
    }
}