package com.akv.cypherx.domain.usecase

import com.akv.cypherx.domain.usecase.accounts.AddNewAccount
import com.akv.cypherx.domain.usecase.accounts.DeleteAccount
import com.akv.cypherx.domain.usecase.accounts.GetAccountById
import com.akv.cypherx.domain.usecase.accounts.GetAllAccounts
import com.akv.cypherx.domain.usecase.accounts.SearchByQuery
import com.akv.cypherx.domain.usecase.accounts.UpdateAccount

data class AccountsDataUseCases(

    val getAllAccounts: GetAllAccounts,
    val getAccountById: GetAccountById,
    val searchByQuery: SearchByQuery,
    val deleteAccount: DeleteAccount,
    val updateAccount: UpdateAccount,
    val addNewAccount: AddNewAccount

)