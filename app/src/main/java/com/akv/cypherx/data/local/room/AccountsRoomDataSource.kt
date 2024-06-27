package com.akv.cypherx.data.local.room

import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteException
import com.akv.cypherx.data.local.AccountsLocalDataSource
import com.akv.cypherx.data.local.room.dao.AccountsDao
import com.akv.cypherx.data.local.room.model.AccountEntity
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AccountsRoomDataSource(
    private val accountsDao: AccountsDao
) : AccountsLocalDataSource {

    override fun getAllAccounts(): Flow<ApiResponse<List<AccountEntity>>> {

        return flow {
            emit(ApiResponse.Loading)
            try {

                accountsDao.getAllAccounts().collect { accounts ->
                    emit(ApiResponse.Success(accounts))
                }

            } catch (e: SQLiteConstraintException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: SQLiteException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }

        }
    }

    override fun searchByQuery(query: String): Flow<ApiResponse<List<AccountEntity>>> {
        return flow {
            emit(ApiResponse.Loading)
            try {

                accountsDao.searchByQuery(query).collect { accounts ->
                    emit(ApiResponse.Success(accounts))
                }

            } catch (e: SQLiteConstraintException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: SQLiteException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }

        }
    }

    override suspend fun addNewAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>> {
        return flow {
            emit(ApiResponse.Loading)
            try {

                accountsDao.addNewAccount(accountEntity)
                emit(ApiResponse.Success(Unit))

            } catch (e: SQLiteConstraintException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: SQLiteException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }
        }
    }

    override suspend fun deleteAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>> {
        return flow {
            emit(ApiResponse.Loading)
            try {

                accountsDao.deleteAccount(accountEntity)
                emit(ApiResponse.Success(Unit))

            } catch (e: SQLiteConstraintException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: SQLiteException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }
        }
    }

    override suspend fun updateAccount(accountEntity: AccountEntity): Flow<ApiResponse<Unit>> {
        return flow {
            emit(ApiResponse.Loading)
            try {

                accountsDao.updateAccount(accountEntity)
                emit(ApiResponse.Success(Unit))

            } catch (e: SQLiteConstraintException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: SQLiteException) {
                emit(ApiResponse.Error(e.message))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.message))
            }
        }
    }

}