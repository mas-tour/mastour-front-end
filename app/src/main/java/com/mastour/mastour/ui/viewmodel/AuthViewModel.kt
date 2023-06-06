package com.mastour.mastour.ui.viewmodel

import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.remote.RegisterResponses
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Login
    private val _loginResponse: MutableStateFlow<UiState<LoginResponses>> = MutableStateFlow(UiState.Loading)
    val loginResponse: StateFlow<UiState<LoginResponses>>
        get() = _loginResponse

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist

    fun changeEmail(email: String){
        _email.value = email
    }
    fun changePassword(password: String){
        _password.value = password
    }
    fun login(){
        viewModelScope.launch {
            repository.login(email.value, password.value).collect{
                _loginResponse.value = it
            }
        }
    }
    fun tryUserExist(){
        viewModelScope.launch {
            repository.getUserExist().collect{
                _userExist.value = it
            }
        }
    }

    // Register
    private val _registerResponse: MutableStateFlow<UiState<RegisterResponses>> = MutableStateFlow(UiState.Loading)

    val registerResponse: StateFlow<UiState<RegisterResponses>>
        get() = _registerResponse

    private val _emailRegister = mutableStateOf("")
    val emailRegister: State<String> get() = _emailRegister

    private val _usernameRegister = mutableStateOf("")
    val usernameRegister: State<String> get() = _usernameRegister

    private val _nameRegister = mutableStateOf("")
    val nameRegister: State<String> get() = _nameRegister

    private val _passwordRegister = mutableStateOf("")
    val passwordRegister: State<String> get() = _passwordRegister

    private val _passwordConfirm = mutableStateOf("")
    val passwordConfirm: State<String> get() = _passwordConfirm

    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> get() = _imageUri

    fun changeEmailRegister(email: String){
        _emailRegister.value = email
    }

    fun changeUsernameRegister(username: String){
        _usernameRegister.value = username
    }

    fun changeNameRegister(name: String){
        _nameRegister.value = name
    }

    fun changePasswordRegister(password: String){
        _passwordRegister.value = password
    }

    fun changePasswordConfirm(password: String){
        _passwordConfirm.value = password
    }

    fun changeUri(uri: Uri) {
        _imageUri.value = uri
    }

    fun register() {
        viewModelScope.launch {
            // TODO: Handle if no photo is selected, maybe default url
            val result = imageUri.value?.let { repository.uploadImage(it) }

            val link = (result?.fold(
                onSuccess = { value ->
                        repository.register(
                            emailRegister.value,
                            usernameRegister.value,
                            nameRegister.value,
                            passwordRegister.value,
                            value
                        ).collect {
                            _registerResponse.value = it
                        }
                },
                onFailure = { exception ->
                    // ...
                }
            ) ?: "Error occured").toString()
        }
    }
}