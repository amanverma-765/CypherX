package com.akv.cypherx.data.local.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.akv.cypherx.data.local.room.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountsDao {

    @Query("SELECT * FROM accounts")
    fun getAllAccounts(): Flow<List<AccountEntity>>

    @Query("SELECT * FROM accounts WHERE accountName LIKE :query OR accountUsername LIKE :query")
    fun searchByQuery(query: String): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNewAccount(accountEntity: AccountEntity)

    @Delete
    suspend fun deleteAccount(accountEntity: AccountEntity)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

}