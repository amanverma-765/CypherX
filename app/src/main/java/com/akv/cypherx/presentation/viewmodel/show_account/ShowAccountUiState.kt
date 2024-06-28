package com.akv.cypherx.presentation.viewmodel.show_account

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.utils.ApiResponse

data class ShowAccountUiState(

    val getAccountByIdResponse: ApiResponse<AccountData> = ApiResponse.IDLE,
    val deleteAccountByIdResponse: ApiResponse<Unit> = ApiResponse.IDLE

)