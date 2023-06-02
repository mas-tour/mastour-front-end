package com.mastour.mastour.util

sealed class UiState <out T>{
    object Loading: UiState<Nothing>()

    data class Success<out T>(
        val data: T?
    ): UiState<T>()

    data class Failure(
        val e: Exception?
    ): UiState<Nothing>()
}
