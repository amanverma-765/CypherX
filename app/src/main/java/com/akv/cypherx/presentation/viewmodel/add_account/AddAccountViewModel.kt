package com.akv.cypherx.presentation.viewmodel.add_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.domain.usecase.AccountsDataUseCases
import com.akv.cypherx.domain.usecase.GoogleScraperUseCases
import com.akv.cypherx.domain.usecase.google.GetWebsiteName
import com.akv.cypherx.security.CryptoManager
import com.akv.cypherx.security.generatePassword
import com.akv.cypherx.security.passwordStrength
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.builtins.UIntArraySerializer
import org.koin.core.KoinApplication.Companion.init


class AddAccountViewModel(
    private val accountsDataUseCases: AccountsDataUseCases,
    private val accountData: AccountData,
    private val cryptoManager: CryptoManager,
    private val googleScraperUseCases: GoogleScraperUseCases
) : ViewModel() {


    private val _addAccountUiState = MutableStateFlow(AddAccountUiState())
    val addAccountUiState = _addAccountUiState.asStateFlow()

    fun onEvent(event: AddAccountUiEvents) {
        when (event) {
            is AddAccountUiEvents.OnAccountNameChange -> onAccountNameChange(event.text)
            is AddAccountUiEvents.OnPasswordChange -> onPasswordChange(event.text)
            is AddAccountUiEvents.OnUserNameChange -> onUserNameChange(event.text)
            is AddAccountUiEvents.ValidateForm -> validateForm()
            is AddAccountUiEvents.GenerateRandomPass -> generateRandomPass(event.length)
            is AddAccountUiEvents.AddNewAccount -> getWebsiteName()
        }
    }

    init {
        if (accountData.id != null) {
            _addAccountUiState.update { state ->
                state.copy(
                    accountName = accountData.accountName,
                    password = accountData.accountPassword,
                    userName = accountData.accountUsername,
                    websiteUrl = accountData.websiteUrl
                )
            }
            validateForm()
        }
        calculatePassStrength()
    }

    private fun onAccountNameChange(text: String) {
        _addAccountUiState.update { state ->
            state.copy(
                accountName = text,
                accountNameError = ""
            )
        }
        validateAccountName()
    }

    private fun onPasswordChange(text: String) {
        _addAccountUiState.update { state ->
            state.copy(
                password = text,
                passwordError = ""
            )
        }
        validatePassword()
        calculatePassStrength()
    }

    private fun onUserNameChange(text: String) {
        _addAccountUiState.update { state ->
            state.copy(
                userName = text,
                userNameError = ""
            )
        }
        validateUserName()
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
                userNameError = errorMessage,
                isUsernameValidated = isValid
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
                passwordError = errorMessage,
                isPasswordValidated = isValid
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
                accountNameError = errorMessage,
                isAccountNameValidated = isValid
            )
        }
        return isValid
    }

    private fun validateForm() {
        validateAccountName()
        validateUserName()
        validatePassword()
    }

    private fun addNewAccount(websiteName: String? = null) {
        Log.e("TAG", "addNewAccount: ")
        viewModelScope.launch {
            val apiResponse = accountsDataUseCases
                .addNewAccount(
                    AccountData(
                        websiteUrl = websiteName,
                        accountName = addAccountUiState.value.accountName,
                        accountUsername = addAccountUiState.value.userName,
                        accountPassword = cryptoManager.encrypt(addAccountUiState.value.password),
                    )
                )
            responseHandler(apiResponse)
        }
    }

    private fun updateAccount(websiteName: String? = null) {
        viewModelScope.launch {
            val apiResponse = accountsDataUseCases
                .updateAccount(
                    AccountData(
                        id = accountData.id,
                        websiteUrl = websiteName,
                        accountName = addAccountUiState.value.accountName,
                        accountUsername = addAccountUiState.value.userName,
                        accountPassword = cryptoManager.encrypt(addAccountUiState.value.password),
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
                        addNewAccountResponse = response
                    )
                }
            }
        }
    }

    private fun generateRandomPass(length: Int) {
        viewModelScope.launch {
            val pass = generatePassword(length)
            _addAccountUiState.update { state ->
                state.copy(
                    password = pass
                )
            }
        }
        calculatePassStrength()
        validateForm()
    }

    private fun calculatePassStrength() {
        viewModelScope.launch {
            Log.e("TAG", "calculatePassStrength: ${addAccountUiState.value.password}")
            val passStrength = passwordStrength(addAccountUiState.value.password)
            _addAccountUiState.update { state ->
                state.copy(
                    passStrength = passStrength
                )
            }
        }
    }

    private fun getWebsiteName() {
        viewModelScope.launch {
            googleScraperUseCases.getWebsiteName(addAccountUiState.value.accountName)
                .collect { response ->
                    when (response) {
                        is ApiResponse.Error -> {
                            if (accountData.id == null)
                                addNewAccount()
                            else updateAccount()
                            _addAccountUiState.update { state ->
                                state.copy(
                                    getWebsiteNameResponse = ApiResponse.Error(response.message)
                                )
                            }
                        }

                        is ApiResponse.IDLE -> ApiResponse.IDLE
                        is ApiResponse.Loading -> ApiResponse.Loading
                        is ApiResponse.Success -> {
                            if (accountData.id == null)
                                addNewAccount(response.data)
                            else updateAccount(response.data)
                            _addAccountUiState.update { state ->
                                state.copy(
                                    getWebsiteNameResponse = ApiResponse.Success(Unit)
                                )
                            }
                        }
                    }
                }
        }
    }
}