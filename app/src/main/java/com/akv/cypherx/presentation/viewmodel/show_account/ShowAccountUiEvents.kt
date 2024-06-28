package com.akv.cypherx.presentation.viewmodel.show_account

sealed class ShowAccountUiEvents {

    data class DeleteAccount(val accountId: Int) : ShowAccountUiEvents()

}