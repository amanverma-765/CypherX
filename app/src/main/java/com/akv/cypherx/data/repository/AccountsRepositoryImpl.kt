package com.akv.cypherx.data.repository

import com.akv.cypherx.data.local.AccountsLocalDataSource
import com.akv.cypherx.data.local.room.mapper.AccountsDataMapper.toAccountData
import com.akv.cypherx.data.local.room.mapper.AccountsDataMapper.toAccountEntity
import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.domain.repository.AccountsRepository
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class AccountsRepositoryImpl(
    private val accountsLocalDataSource: AccountsLocalDataSource
) : AccountsRepository {

    override fun getAllAccounts(): Flow<ApiResponse<List<AccountData>>> {
        return flow {
            accountsLocalDataSource.getAllAccounts().map { response ->
                when (response) {

                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(response.message))
                    }

                    is ApiResponse.Loading -> {
                        emit(ApiResponse.Loading)
                    }

                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.data.map { it.toAccountData() }))
                    }
                }
            }
        }
    }

    override fun searchByQuery(query: String): Flow<ApiResponse<List<AccountData>>> {
        return flow {
            accountsLocalDataSource.searchByQuery(query).map { response ->
                when (response) {

                    is ApiResponse.Loading -> {
                        emit(ApiResponse.Loading)
                    }

                    is ApiResponse.Error -> {
                        emit(ApiResponse.Error(response.message))
                    }

                    is ApiResponse.Success -> {
                        emit(ApiResponse.Success(response.data.map { it.toAccountData() }))
                    }
                }
            }
        }
    }

    override suspend fun addNewAccount(accountData: AccountData): Flow<ApiResponse<Unit>> {
        return flow {
            accountsLocalDataSource.addNewAccount(accountData.toAccountEntity()).map { response ->
                when (response) {
                    is ApiResponse.Error -> ApiResponse.Error(response.message)
                    is ApiResponse.Loading -> ApiResponse.Loading
                    is ApiResponse.Success -> ApiResponse.Success(response.data)
                }
            }
        }
    }

    override suspend fun deleteAccount(accountData: AccountData): Flow<ApiResponse<Unit>> {
        return flow {
            accountsLocalDataSource.deleteAccount(accountData.toAccountEntity()).map { response ->
                when (response) {
                    is ApiResponse.Error -> ApiResponse.Error(response.message)
                    is ApiResponse.Loading -> ApiResponse.Loading
                    is ApiResponse.Success -> ApiResponse.Success(response.data)
                }
            }
        }
    }

    override suspend fun updateAccount(accountData: AccountData): Flow<ApiResponse<Unit>> {
        return flow {
            accountsLocalDataSource.updateAccount(accountData.toAccountEntity()).map { response ->
                when (response) {
                    is ApiResponse.Error -> ApiResponse.Error(response.message)
                    is ApiResponse.Loading -> ApiResponse.Loading
                    is ApiResponse.Success -> ApiResponse.Success(response.data)
                }
            }
        }
    }

}