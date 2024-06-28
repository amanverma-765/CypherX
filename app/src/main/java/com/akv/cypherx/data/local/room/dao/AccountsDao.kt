package com.akv.cypherx.data.local.room.dao

import androidx.room.Dao
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

    @Query("SELECT * FROM accounts WHERE id IS :accountId")
    fun getAccountById(accountId: Int): Flow<AccountEntity>

    @Query("SELECT * FROM accounts WHERE LOWER(accountName) LIKE LOWER(:query) OR LOWER(accountUsername) LIKE LOWER(:query)")
    fun searchByQuery(query: String): Flow<List<AccountEntity>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addNewAccount(accountEntity: AccountEntity)

    @Query("DELETE FROM accounts WHERE id IS :accountId")
    suspend fun deleteAccount(accountId: Int)

    @Update
    suspend fun updateAccount(accountEntity: AccountEntity)

}