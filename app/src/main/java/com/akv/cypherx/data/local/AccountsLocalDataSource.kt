package com.akv.cypherx.data.local

import com.akv.cypherx.data.local.room.model.AccountEntity
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AccountsLocalDataSource {

    fun getAllAccounts(): Flow<ApiResponse<List<AccountEntity>>>
    fun searchByQuery(query: String): Flow<ApiResponse<List<AccountEntity>>>

    suspend fun addNewAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>>
    suspend fun deleteAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>>
    suspend fun updateAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>>

}