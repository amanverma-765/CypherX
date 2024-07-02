package com.akv.cypherx.domain.usecase.google

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.domain.repository.AccountsRepository
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class GetWebsiteName(
    private val accountsRepository: AccountsRepository
) {
    operator fun invoke(searchName: String): Flow<ApiResponse<String>> {
        return accountsRepository.getWebsiteName(searchName)
    }
}