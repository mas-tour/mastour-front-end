package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist
    fun deleteSession(){
        viewModelScope.launch {
            repository.deleteSession()
        }
    }
    fun tryUserExist(){
        viewModelScope.launch {
            repository.getUserExist().collect{
                _userExist.value = it
            }
        }
    }
}