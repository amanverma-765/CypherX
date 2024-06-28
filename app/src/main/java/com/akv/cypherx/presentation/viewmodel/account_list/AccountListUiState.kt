package com.akv.cypherx.presentation.viewmodel.account_list

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.utils.ApiResponse

data class AccountListUiState(

    val getAllDataResponse: ApiResponse<List<AccountData>> = ApiResponse.IDLE,

    val searchQuery: String = ""

)