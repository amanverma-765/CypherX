package com.akv.cypherx.data.koin

import androidx.room.Room
import com.akv.cypherx.data.local.AccountsLocalDataSource
import com.akv.cypherx.data.local.room.AccountsDatabase
import com.akv.cypherx.data.local.room.AccountsRoomDataSource
import com.akv.cypherx.data.local.room.dao.AccountsDao
import com.akv.cypherx.data.repository.AccountsRepositoryImpl
import com.akv.cypherx.domain.repository.AccountsRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val accountsModule = module {

    single<AccountsDatabase> {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AccountsDatabase::class.java,
            name = "AccountsDatabase"
        ).build()
    }

    single<AccountsDao> {
        val localDatabase = get<AccountsDatabase>()
        localDatabase.accountsDao()
    }

    single<AccountsLocalDataSource> {
        AccountsRoomDataSource(accountsDao = get())
    }

    single<AccountsRepository> {
        AccountsRepositoryImpl(accountsLocalDataSource = get())
    }

}