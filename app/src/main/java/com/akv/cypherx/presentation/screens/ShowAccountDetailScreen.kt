package com.akv.cypherx.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import com.akv.cypherx.presentation.components.AccountDetailItem
import com.akv.cypherx.presentation.viewmodel.show_account.ShowAccountUiEvents
import com.akv.cypherx.presentation.viewmodel.show_account.ShowAccountUiState
import com.akv.cypherx.presentation.viewmodel.show_account.ShowAccountViewModel
import com.akv.cypherx.utils.ApiResponse
import com.akv.cypherx.utils.toast
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

data class ShowAccountDetailScreen(private val accountId: Int) : Screen {
    @Composable
    override fun Content() {

        val sheetNavigator = LocalBottomSheetNavigator.current
        val showAccountVm = koinViewModel<ShowAccountViewModel>(
            parameters = { parametersOf(accountId) }
        )
        val uiState by showAccountVm.showAccountUiState.collectAsState()
        ShowAccountDetailContent(
            uiState = uiState,
            uiEvents = showAccountVm::onEvent,
            accountId = accountId,
            sheetNavigator = sheetNavigator
        )
    }
}

@Composable
fun ShowAccountDetailContent(
    modifier: Modifier = Modifier,
    uiState: ShowAccountUiState,
    uiEvents: (ShowAccountUiEvents) -> Unit,
    accountId: Int,
    sheetNavigator: BottomSheetNavigator
) {

    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current

    LaunchedEffect(key1 = uiState.deleteAccountByIdResponse) {
        when (val response = uiState.deleteAccountByIdResponse) {
            is ApiResponse.Error -> {
                context.toast(response.message)
            }

            is ApiResponse.Loading -> {
                context.toast("Deleting...")
            }

            is ApiResponse.Success -> {
                context.toast("Deleted")
                sheetNavigator.hide()
            }

            ApiResponse.IDLE -> Unit
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 12.dp, vertical = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Account Details",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.primary
        )
        when (val response = uiState.getAccountByIdResponse) {
            is ApiResponse.Error -> {
                context.toast(response.message)
            }

            is ApiResponse.Loading -> {
                CircularProgressIndicator()
            }

            is ApiResponse.Success -> {

                AccountDetailItem(
                    headlineText = response.data.accountName,
                    overLineText = "Account Type",
                )

                AccountDetailItem(
                    headlineText = response.data.accountUsername,
                    overLineText = "Username/Email",
                    onCopyClicked = {
                        clipboard.setText(AnnotatedString(response.data.accountUsername))
                        context.toast("Username copied to clipboard.", lengthShort = true)
                    }
                )

                AccountDetailItem(
                    headlineText = "***********************",
                    overLineText = "Password",
                    onCopyClicked = {
                        clipboard.setText(AnnotatedString(response.data.accountPassword))
                        context.toast("Password copied to clipboard.", lengthShort = true)
                    }
                )
            }

            ApiResponse.IDLE -> Unit
        }


        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {

            Button(
                onClick = {
                    uiState.getAccountByIdResponse.apply {
                        if (this is ApiResponse.Success) {
                            sheetNavigator.show(AddNewAccountScreen(this.data))
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "Edit",
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = { uiEvents(ShowAccountUiEvents.DeleteAccount(accountId)) },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = "Delete",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

