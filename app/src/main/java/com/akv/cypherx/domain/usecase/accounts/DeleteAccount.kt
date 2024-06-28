package com.akv.cypherx.domain.usecase.accounts

import com.akv.cypherx.domain.repository.AccountsRepository
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class DeleteAccount(
    private val accountsRepository: AccountsRepository
) {
    operator fun invoke(accountId: Int): Flow<ApiResponse<Unit>> {
        return accountsRepository.deleteAccount(accountId)
    }
}