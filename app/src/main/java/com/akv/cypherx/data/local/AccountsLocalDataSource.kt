package com.akv.cypherx.data.local

import com.akv.cypherx.data.local.room.model.AccountEntity
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface AccountsLocalDataSource {

    fun getAllAccounts(): Flow<ApiResponse<List<AccountEntity>>>
    fun getAccountById(accountId: Int): Flow<ApiResponse<AccountEntity>>
    fun searchByQuery(query: String): Flow<ApiResponse<List<AccountEntity>>>

    fun addNewAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>>
    fun deleteAccount(accountId: Int): Flow<ApiResponse<Unit>>
    fun updateAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>>

}