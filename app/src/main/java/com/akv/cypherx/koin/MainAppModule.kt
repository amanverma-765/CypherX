package com.akv.cypherx.koin

import androidx.room.Room
import com.akv.cypherx.data.local.AccountsLocalDataSource
import com.akv.cypherx.data.local.room.AccountsDatabase
import com.akv.cypherx.data.local.room.AccountsRoomDataSource
import com.akv.cypherx.data.local.room.dao.AccountsDao
import com.akv.cypherx.data.remote.GoogleScraper
import com.akv.cypherx.data.remote.SimilarNamedWebsiteScraper
import com.akv.cypherx.data.repository.AccountsRepositoryImpl
import com.akv.cypherx.domain.repository.AccountsRepository
import com.akv.cypherx.domain.usecase.AccountsDataUseCases
import com.akv.cypherx.domain.usecase.GoogleScraperUseCases
import com.akv.cypherx.domain.usecase.accounts.AddNewAccount
import com.akv.cypherx.domain.usecase.accounts.DeleteAccount
import com.akv.cypherx.domain.usecase.accounts.GetAccountById
import com.akv.cypherx.domain.usecase.accounts.GetAllAccounts
import com.akv.cypherx.domain.usecase.accounts.SearchByQuery
import com.akv.cypherx.domain.usecase.accounts.UpdateAccount
import com.akv.cypherx.domain.usecase.google.GetWebsiteName
import com.akv.cypherx.presentation.viewmodel.account_list.AccountListViewModel
import com.akv.cypherx.presentation.viewmodel.add_account.AddAccountViewModel
import com.akv.cypherx.presentation.viewmodel.show_account.ShowAccountViewModel
import com.akv.cypherx.security.CryptoManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainAppModule = module {

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
        AccountsRepositoryImpl(
            accountsLocalDataSource = get(),
            googleScraper = get()
        )
    }

    single {
        AccountsDataUseCases(
            getAllAccounts = GetAllAccounts(accountsRepository = get()),
            getAccountById = GetAccountById(accountsRepository = get()),
            searchByQuery = SearchByQuery(accountsRepository = get()),
            deleteAccount = DeleteAccount(accountsRepository = get()),
            updateAccount = UpdateAccount(accountsRepository = get()),
            addNewAccount = AddNewAccount(accountsRepository = get())
        )
    }

    viewModel {
        AccountListViewModel(
            accountsDataUseCases = get(),
        )
    }

    viewModel {
        AddAccountViewModel(
            accountsDataUseCases = get(),
            accountData = get(),
            cryptoManager = get(),
            googleScraperUseCases = get()
        )
    }

    viewModel {
        ShowAccountViewModel(
            accountsDataUseCases = get(),
            accountId = get(),
            cryptoManager = get()
        )
    }

    single { CryptoManager() }

    single<GoogleScraper> { SimilarNamedWebsiteScraper() }

    single {
        GoogleScraperUseCases(
            getWebsiteName = GetWebsiteName(accountsRepository = get())
        )
    }

}