package com.akv.cypherx.domain.repository

import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AccountsRepository {

    fun getAllAccounts(): Flow<ApiResponse<List<AccountData>>>
    fun searchByQuery(query: String): Flow<ApiResponse<List<AccountData>>>

    suspend fun addNewAccount(accountData: AccountData): Flow<ApiResponse<Unit>>
    suspend fun deleteAccount(accountData: AccountData): Flow<ApiResponse<Unit>>
    suspend fun updateAccount(accountData: AccountData): Flow<ApiResponse<Unit>>

}