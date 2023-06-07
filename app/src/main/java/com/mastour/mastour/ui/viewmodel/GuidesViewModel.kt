package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mastour.mastour.data.remote.DataGuides
import com.mastour.mastour.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GuidesViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _userToken = mutableStateOf("")

    fun tryUserToken(){
        viewModelScope.launch {
            repository.getUserToken().collect{
                _userToken.value = it
            }
        }
    }

    fun getGuides(): Flow<PagingData<DataGuides>> = repository.getGuides("Bearer ${_userToken.value}").cachedIn(viewModelScope)
}