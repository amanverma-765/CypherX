package com.akv.cypherx.domain.usecase.accounts

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.domain.repository.AccountsRepository
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class UpdateAccount(
    private val accountsRepository: AccountsRepository
) {
    suspend operator fun invoke(accountData: AccountData): Flow<ApiResponse<Unit>> {
        return accountsRepository.updateAccount(accountData)
    }
}