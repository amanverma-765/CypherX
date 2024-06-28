package com.akv.cypherx.presentation.viewmodel.add_account

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.utils.ApiResponse

data class AddAccountUiState(

    val addNewAccountResponse: ApiResponse<Unit> = ApiResponse.IDLE,
    val getAccountByIdResponse: ApiResponse<AccountData> = ApiResponse.IDLE,
    val updateAccountResponse: ApiResponse<Unit> = ApiResponse.IDLE,

    val accountName: String = "",
    val userName: String = "",
    val password: String = "",

    val accountNameError: String = "",
    val userNameError: String = "",
    val passwordError: String = "",

    val isFormValidated: Boolean = false
)