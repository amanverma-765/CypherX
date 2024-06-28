package com.akv.cypherx.presentation.viewmodel.add_account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.domain.usecase.AccountsDataUseCases
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class AddAccountViewModel(
    private val accountsDataUseCases: AccountsDataUseCases,
    private val accountData: AccountData
) : ViewModel() {


    private val _addAccountUiState = MutableStateFlow(AddAccountUiState())
    val addAccountUiState = _addAccountUiState.asStateFlow()

    fun onEvent(event: AddAccountUiEvents) {
        when (event) {

            is AddAccountUiEvents.AddNewAccount -> {
                if (accountData.id == null)
                    addNewAccount()
                else updateAccount()
            }

            is AddAccountUiEvents.OnAccountNameChange -> onAccountNameChange(event.text)
            is AddAccountUiEvents.OnPasswordChange -> onPasswordChange(event.text)
            is AddAccountUiEvents.OnUserNameChange -> onUserNameChange(event.text)
            is AddAccountUiEvents.ValidateForm -> validateForm()
        }
    }

    init {
        _addAccountUiState.update { state ->
            state.copy(
                accountName = accountData.accountName,
                password = accountData.accountPassword,
                userName = accountData.accountUsername
            )
        }
        if (accountData.id != null) validateForm()
    }

    private fun onAccountNameChange(text: String) {
        _addAccountUiState.update { state ->
            state.copy(
                accountName = text,
                accountNameError = ""
            )
        }
    }

    private fun onPasswordChange(text: String) {
        validateForm()
        _addAccountUiState.update { state ->
            state.copy(
                password = text,
                passwordError = ""
            )
        }
    }

    private fun onUserNameChange(text: String) {
        _addAccountUiState.update { state ->
            state.copy(
                userName = text,
                userNameError = ""
            )
        }
    }

    private fun validateUserName(): Boolean {
        val userName = addAccountUiState.value.userName
        var isValid = true
        var errorMessage = ""
        if (userName.isBlank() || userName.isEmpty()) {
            errorMessage = "This field cant be empty"
            isValid = false
        }
        _addAccountUiState.update { state ->
            state.copy(
                userNameError = errorMessage
            )
        }
        return isValid
    }

    private fun validatePassword(): Boolean {
        val password = addAccountUiState.value.password
        var isValid = true
        var errorMessage = ""
        if (password.isBlank() || password.isEmpty()) {
            errorMessage = "This field cant be empty"
            isValid = false
        } else if (password.length < 8) {
            errorMessage = "Password must be at least 8 characters"
            isValid = false
        }
        _addAccountUiState.update { state ->
            state.copy(
                passwordError = errorMessage
            )
        }
        return isValid
    }

    private fun validateAccountName(): Boolean {
        val accountName = addAccountUiState.value.accountName
        var isValid = true
        var errorMessage = ""
        if (accountName.isBlank() || accountName.isEmpty()) {
            errorMessage = "This field cant be empty"
            isValid = false
        }
        _addAccountUiState.update { state ->
            state.copy(
                accountNameError = errorMessage
            )
        }
        return isValid
    }

    private fun validateForm() {
        _addAccountUiState.update { state ->
            state.copy(isFormValidated = validateAccountName() && validateUserName() && validatePassword())
        }
    }

    private fun addNewAccount() {
        viewModelScope.launch {
            val apiResponse = accountsDataUseCases
                .addNewAccount(
                    AccountData(
                        accountName = addAccountUiState.value.accountName,
                        accountUsername = addAccountUiState.value.userName,
                        accountPassword = addAccountUiState.value.password,
                    )
                )
            responseHandler(apiResponse)
        }
    }

    private fun updateAccount() {
        viewModelScope.launch {
            val apiResponse = accountsDataUseCases
                .updateAccount(
                    AccountData(
                        id = accountData.id,
                        accountName = addAccountUiState.value.accountName,
                        accountUsername = addAccountUiState.value.userName,
                        accountPassword = addAccountUiState.value.password,
                    )
                )
            responseHandler(apiResponse)
        }
    }

    private fun responseHandler(apiResponse: Flow<ApiResponse<Unit>>) {
        viewModelScope.launch {
            apiResponse.collect { response ->
                _addAccountUiState.update { state ->
                    state.copy(
                        addNewAccountResponse = when (response) {
                            is ApiResponse.Error -> ApiResponse.Error(response.message)
                            is ApiResponse.Loading -> ApiResponse.Loading
                            is ApiResponse.Success -> ApiResponse.Success(response.data)
                            is ApiResponse.IDLE -> ApiResponse.IDLE
                        }
                    )
                }
            }
        }
    }

}