package com.mastour.mastour.util

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : UiState<T>()

    data class Failure(
        val e: Exception?
    ) : UiState<Nothing>()
}

sealed class AuthUiState<out T> {
    object Idle : AuthUiState<Nothing>()
    object Load : AuthUiState<Nothing>()

    data class Success<out T>(
        val data: T?
    ) : AuthUiState<T>()

    data class Failure(
        val e: Exception?
    ) : AuthUiState<Nothing>()
}

