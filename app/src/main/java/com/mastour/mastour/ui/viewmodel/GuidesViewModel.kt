package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mastour.mastour.data.remote.CategoriesHelper
import com.mastour.mastour.data.remote.DataGuides
import com.mastour.mastour.data.remote.DetailGuidesResponse
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuidesViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _userToken = mutableStateOf("")

    private val _detailResponse: MutableStateFlow<UiState<DetailGuidesResponse>> = MutableStateFlow(UiState.Loading)
    val detailResponse: StateFlow<UiState<DetailGuidesResponse>>
        get() = _detailResponse

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    private val _categoriesResponse: MutableStateFlow<UiState<CategoriesHelper>> = MutableStateFlow(UiState.Loading)
    val categoriesResponse: StateFlow<UiState<CategoriesHelper>>
        get() = _categoriesResponse


    fun changeQuery(query: String){
        _query.value = query
    }

    fun tryUserToken(){
        viewModelScope.launch {
            repository.getUserToken().collect{
                _userToken.value = it
            }
        }
    }

    fun getGuides(query: String = "", cityId: String? = null, categoryId: String? = null): Flow<PagingData<DataGuides>> =
        repository.getGuides(bearer = "Bearer ${_userToken.value}", query = query, cityId = cityId, categoryId = categoryId).cachedIn(viewModelScope)
    fun getDetailedGuide(id: String){
        viewModelScope.launch {
            repository.detailedGuides(id, _userToken.value).collect{
                _detailResponse.value = it
            }
        }
    }
    fun getCategories(){
        viewModelScope.launch {
            repository.getCategories().collect{
                _categoriesResponse.value = it
            }
        }
    }
}