package com.mastour.mastour.ui.viewmodel

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mastour.mastour.R
import com.mastour.mastour.data.remote.LoginResponses
import com.mastour.mastour.data.remote.RegisterResponses
import com.mastour.mastour.data.repository.Repository
import com.mastour.mastour.util.UiState
import com.mastour.mastour.util.convertBitmapToBase64
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

    // Change Picture versi bitmap
    private val _picture = mutableStateOf<Bitmap?>(null)
    val picture: State<Bitmap?> get() = _picture
    fun changePicture(picture: Bitmap) {
        _picture.value = picture
    }

    // Change picture Uri
    private val _imageUri = mutableStateOf<Uri?>(null)
    val imageUri: State<Uri?> get() = _imageUri
//    fun changeUri(uri: Uri) {
//        _imageUri.value = imageUri
//    }

    // Klik register -> Upload foto ImgurAPI -> Get Link from result -> terus POST ke MasTourAPI
//    fun register() {
//        viewModelScope.launch {
//            val pictureLink = picture.value?.let { uploadImage(it) }
//
//            repository.register(
//                emailRegister.value,
//                usernameRegister.value,
//                nameRegister.value,
//                passwordRegister.value,
//                pictureLink
//            ).collect {
//                _registerResponse.value = it
//            }
//        }
//    }

    // Imgur API
//    fun uploadImage(bitmap: Bitmap) {
//        val image = convertBitmapToBase64(bitmap)
//        viewModelScope.launch {
//            repository.uploadImage(image)
//        }
//    }
}