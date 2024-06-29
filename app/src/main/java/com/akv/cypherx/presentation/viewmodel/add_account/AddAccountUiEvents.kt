package com.akv.cypherx.presentation.viewmodel.add_account

sealed class AddAccountUiEvents {

    data object AddNewAccount : AddAccountUiEvents()

    data class OnAccountNameChange(val text: String) : AddAccountUiEvents()
    data class OnUserNameChange(val text: String) : AddAccountUiEvents()
    data class OnPasswordChange(val text: String) : AddAccountUiEvents()

    data object ValidateForm : AddAccountUiEvents()

    data class GenerateRandomPass(val length: Int) : AddAccountUiEvents()
}