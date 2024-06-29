package com.akv.cypherx.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.presentation.components.AccountLeadingItem
import com.akv.cypherx.presentation.components.AccountTextField
import com.akv.cypherx.presentation.components.PasswordFieldSupportingContent
import com.akv.cypherx.presentation.viewmodel.add_account.AddAccountUiEvents
import com.akv.cypherx.presentation.viewmodel.add_account.AddAccountUiState
import com.akv.cypherx.presentation.viewmodel.add_account.AddAccountViewModel
import com.akv.cypherx.utils.ApiResponse
import com.akv.cypherx.utils.toast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class AddNewAccountScreen(val accountData: AccountData? = null) : Screen {
    @Composable
    override fun Content() {

        val sheetNavigator = LocalBottomSheetNavigator.current
        val addAccountVm = koinViewModel<AddAccountViewModel>(
            parameters = { parametersOf(accountData ?: AccountData(null, "", "", "")) }
        )
        val uiState by addAccountVm.addAccountUiState.collectAsState()
        AddNewAccountContent(
            uiState = uiState,
            uiEvents = addAccountVm::onEvent,
            sheetNavigator = sheetNavigator,
        )
    }
}

@Composable
private fun AddNewAccountContent(
    modifier: Modifier = Modifier,
    uiState: AddAccountUiState,
    uiEvents: (AddAccountUiEvents) -> Unit,
    sheetNavigator: BottomSheetNavigator
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = uiState.addNewAccountResponse) {
        when (val response = uiState.addNewAccountResponse) {
            is ApiResponse.Error -> {
                context.toast(response.message)
            }

            is ApiResponse.Loading -> {
                context.toast("Saving...")
            }

            is ApiResponse.Success -> {
                context.toast("Account Saved")
                sheetNavigator.hide()
            }

            ApiResponse.IDLE -> Unit
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(2.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .imePadding()
            .verticalScroll(rememberScrollState())
    ) {

        Text(
            text = "Add New Account",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )

        AccountTextField(
            text = uiState.accountName,
            onTextChange = { uiEvents(AddAccountUiEvents.OnAccountNameChange(it)) },
            isError = uiState.accountNameError.isNotEmpty(),
            label = "Account Name",
            supportingText = { Text(text = uiState.accountNameError) },
            leadingIcon = {
                AccountLeadingItem(
                    modifier = Modifier.size(30.dp),
                    title = uiState.accountName
                )
            }
        )

        AccountTextField(
            text = uiState.userName,
            onTextChange = { uiEvents(AddAccountUiEvents.OnUserNameChange(it)) },
            isError = uiState.userNameError.isNotEmpty(),
            label = "Username/Email",
            supportingText = { Text(text = uiState.userNameError) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Person2,
                    contentDescription = "User"
                )
            },
        )

        AccountTextField(
            text = uiState.password,
            onTextChange = { uiEvents(AddAccountUiEvents.OnPasswordChange(it)) },
            isError = uiState.passwordError.isNotEmpty(),
            label = "Password",
            isPassword = true,
            imeAction = ImeAction.Done,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Password,
                    contentDescription = "Password"
                )
            },
            supportingText = {
                PasswordFieldSupportingContent(
                    errorText = uiState.passwordError,
                    passStrength = uiState.passStrength,
                    generatePassword = { uiEvents(AddAccountUiEvents.GenerateRandomPass(it)) },
                )
            },
        )

        // Save Button
        Button(
            onClick = {
                uiEvents(AddAccountUiEvents.ValidateForm)
                if (uiState.isAccountNameValidated && uiState.isPasswordValidated && uiState.isUsernameValidated) {
                    uiEvents(AddAccountUiEvents.AddNewAccount)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(text = "Save Account")
        }
    }
}