package com.mastour.mastour.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.CategoriesHelper
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _categoriesResponse: MutableStateFlow<UiState<CategoriesHelper>> = MutableStateFlow(UiState.Loading)
    val categoriesResponse: StateFlow<UiState<CategoriesHelper>>
        get() = _categoriesResponse

    fun getCategories(){
        viewModelScope.launch {
            repository.getCategories().collect{
                _categoriesResponse.value = it
            }
        }
    }
}