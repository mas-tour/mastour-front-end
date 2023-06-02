package com.mastour.mastour.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _loginResponse: MutableStateFlow<UiState<LoginResponses>> = MutableStateFlow(UiState.Loading)
    val loginResponse: StateFlow<UiState<LoginResponses>>
        get() = _loginResponse

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    fun changeEmail(email: String){
        _email.value = email
    }
    fun changePassword(password: String){
        _password.value = password
    }
    fun login(){
        viewModelScope.launch {
            repository.login(_email.value, _password.value).collect{
                _loginResponse.value = it
            }
        }
    }
}