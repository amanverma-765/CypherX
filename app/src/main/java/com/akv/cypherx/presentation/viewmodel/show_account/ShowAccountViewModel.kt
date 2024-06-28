package com.akv.cypherx.presentation.viewmodel.show_account

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akv.cypherx.domain.usecase.AccountsDataUseCases
import com.akv.cypherx.utils.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShowAccountViewModel(
    private val accountsDataUseCases: AccountsDataUseCases,
    private val accountId: Int
) : ViewModel() {

    private val _showAccountUiState = MutableStateFlow(ShowAccountUiState())
    val showAccountUiState = _showAccountUiState.asStateFlow()

    fun onEvent(event: ShowAccountUiEvents) {
        when (event) {
            is ShowAccountUiEvents.DeleteAccount -> deleteAccountById(event.accountId)
        }
    }

    init {
        viewModelScope.launch {
            Log.e("TAG", ": $accountId")
            getAccountById(accountId)
        }
    }

    private fun getAccountById(accountId: Int) {
        viewModelScope.launch {
            accountsDataUseCases
                .getAccountById(accountId)
                .collect { response ->
                    _showAccountUiState.update { state ->
                        state.copy(
                            getAccountByIdResponse = when (response) {
                                is ApiResponse.Error -> ApiResponse.Error(response.message)
                                is ApiResponse.Loading -> ApiResponse.Loading
                                is ApiResponse.Success -> ApiResponse.Success(response.data)
                                ApiResponse.IDLE -> ApiResponse.IDLE
                            }
                        )
                    }
                }
        }
    }


    private fun deleteAccountById(accountId: Int) {
        viewModelScope.launch {
            accountsDataUseCases
                .deleteAccount(accountId)
                .collect { response ->
                    _showAccountUiState.update { state ->
                        state.copy(
                            deleteAccountByIdResponse = when (response) {
                                is ApiResponse.Error -> ApiResponse.Error(response.message)
                                is ApiResponse.Loading -> ApiResponse.Loading
                                is ApiResponse.Success -> ApiResponse.Success(response.data)
                                ApiResponse.IDLE -> ApiResponse.IDLE
                            }
                        )
                    }
                }
        }
    }
}