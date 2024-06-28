package com.akv.cypherx.utils

sealed interface ApiResponse<out R> {
    data class Success<out R>(val data: R) : ApiResponse<R>
    data class Error<out R>(val message: String?) : ApiResponse<R>
    data object Loading : ApiResponse<Nothing>
    data object IDLE : ApiResponse<Nothing>
}