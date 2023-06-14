package com.mastour.mastour.ui.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.remote.RegisterResponses
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.AuthUiState
import com.mastour.mastour.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    // Login
    private val _loginResponse: MutableStateFlow<AuthUiState<LoginResponses>> = MutableStateFlow(AuthUiState.Idle)
    val loginResponse: StateFlow<AuthUiState<LoginResponses>>
        get() = _loginResponse

    private val _email = mutableStateOf("")
    val email: State<String> get() = _email

    private val _password = mutableStateOf("")
    val password: State<String> get() = _password

    private val _userExist = mutableStateOf(false)
    val userExist: State<Boolean> get() = _userExist

    val registerChecklist = mutableListOf(false, false, false, false)

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
    private val _registerResponse: MutableStateFlow<AuthUiState<RegisterResponses>> = MutableStateFlow(AuthUiState.Idle)
    val registerResponse: StateFlow<AuthUiState<RegisterResponses>>
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

    private val _imageUri = mutableStateOf<Uri>(Uri.EMPTY)
    val imageUri: State<Uri> get() = _imageUri

    private val _selectedGender = mutableStateOf("male")
    private val selectedGender: State<String> get() = _selectedGender

    fun changeGender(gender: String) {
        _selectedGender.value = gender
    }

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
            _registerResponse.value = AuthUiState.Load
            val result = imageUri.value.let { repository.uploadImage(it) }

            val link = (result.fold(
                onSuccess = { value ->
                        repository.register(
                            emailRegister.value,
                            usernameRegister.value,
                            nameRegister.value,
                            passwordRegister.value,
                            gender = selectedGender.value,
                            value
                        ).collect {
                            _registerResponse.value = it
                        }
                },
                onFailure = { _ ->
                    // TODO: ...
                }
            ) ?: "Error occured").toString()
        }
    }
}