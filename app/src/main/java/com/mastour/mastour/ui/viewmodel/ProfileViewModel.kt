package com.mastour.mastour.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.local.UserData
import com.mastour.mastour.data.remote.ProfileResponse
import com.mastour.mastour.data.remote.SurveyResponse
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _userToken = mutableStateOf("")
    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist
    fun deleteSession() {
        viewModelScope.launch {
            repository.deleteSession()
        }
    }

    fun tryUserExist() {
        viewModelScope.launch {
            repository.getUserExist().collect {
                _userExist.value = it
            }
        }
    }

    fun tryUserToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }

    private val _profileResponse: MutableStateFlow<UiState<ProfileResponse>> = MutableStateFlow(UiState.Loading)
    val profileResponse: StateFlow<UiState<ProfileResponse>>
        get() = _profileResponse

    fun getProfile() {
        viewModelScope.launch {
            repository.getProfile("Bearer ${_userToken.value}").collect {
                _profileResponse.value = it
            }
        }
    }

    private val _userData = mutableStateOf(UserData())
    private val userData: State<UserData> get() = _userData

    fun changeUserData(userData: UserData) {
        _userData.value = userData
    }

    private val _birthDate = mutableStateOf<Long>(0)
    private val _gender = mutableStateOf("")
    private val _phoneNumber = mutableStateOf("")
    val phoneNumber: State<String> get() = _phoneNumber

    fun changeBirthDate(timestamp: Long) {
        _birthDate.value = timestamp
    }

    fun changeGender(gender: String) {
        _gender.value = gender
    }

    fun changeNumber(number: String) {
        _phoneNumber.value = number
    }

    fun putNumber() {
        _userData.value.phoneNumber = _phoneNumber.value
        Log.d("PhoneNumber", _userToken.value)
        viewModelScope.launch {
            repository.updateProfile(userData.value, _userToken.value).collect {
                _profileResponse.value = it
            }
        }
    }

    fun putGender() {
        _userData.value.gender = _gender.value
        Log.d("PhoneNumber", _userToken.value)
        viewModelScope.launch {
            repository.updateProfile(userData.value, _userToken.value).collect {
                _profileResponse.value = it
            }
        }
    }

    fun putBirthDate() {
        _userData.value.birthDate = _birthDate.value
        Log.d("PhoneNumber", _userToken.value)
        viewModelScope.launch {
            repository.updateProfile(userData.value, _userToken.value).collect {
                _profileResponse.value = it
            }
        }
    }
}