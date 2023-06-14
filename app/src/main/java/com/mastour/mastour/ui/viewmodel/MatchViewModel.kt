package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.CategoriesHelper
import com.mastour.mastour.data.remote.ResponseSurveyResults
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _surveyResultsResponse: MutableStateFlow<UiState<ResponseSurveyResults>> =
        MutableStateFlow(
            UiState.Loading
        )
    val surveyResultsResponse: StateFlow<UiState<ResponseSurveyResults>>
        get() = _surveyResultsResponse

    private val _userToken = mutableStateOf("")

    private val _idCity = mutableStateOf("")
    val idCity: State<String> get() = _idCity

    val todoListState = mutableStateListOf(0, 0, 0, 0, 0, 0, 0, 0)

    private val _categoriesResponse: MutableStateFlow<UiState<CategoriesHelper>> =
        MutableStateFlow(UiState.Loading)
    val categoriesResponse: StateFlow<UiState<CategoriesHelper>>
        get() = _categoriesResponse

    fun getCategories() {
        viewModelScope.launch {
            repository.getCategories().collect {
                _categoriesResponse.value = it
            }
        }
    }


    fun changeIdCity(email: String) {
        _idCity.value = email
    }

    fun tryUserToken() {
        viewModelScope.launch {
            repository.getUserToken().collect {
                _userToken.value = it
            }
        }
    }

    fun getSurveyResults() {
        viewModelScope.launch {
            repository.getSurveyResults(
                cityId = _idCity.value,
                pickedCategories = todoListState,
                token = _userToken.value
            ).collect {
                _surveyResultsResponse.value = it
            }
        }
    }
}