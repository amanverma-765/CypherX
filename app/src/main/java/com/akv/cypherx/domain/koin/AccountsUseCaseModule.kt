package com.akv.cypherx.domain.koin

import com.akv.cypherx.domain.usecase.AccountsDataUseCases
import com.akv.cypherx.domain.usecase.accounts.AddNewAccount
import com.akv.cypherx.domain.usecase.accounts.DeleteAccount
import com.akv.cypherx.domain.usecase.accounts.GetAllAccounts
import com.akv.cypherx.domain.usecase.accounts.SearchByQuery
import com.akv.cypherx.domain.usecase.accounts.UpdateAccount
import org.koin.dsl.module

val accountsUseCaseModule = module {

    single {
        AccountsDataUseCases(
            getAllAccounts = GetAllAccounts(accountsRepository = get()),
            searchByQuery = SearchByQuery(accountsRepository = get()),
            deleteAccount = DeleteAccount(accountsRepository = get()),
            updateAccount = UpdateAccount(accountsRepository = get()),
            addNewAccount = AddNewAccount(accountsRepository = get())
        )
    }
}