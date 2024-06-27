package com.akv.cypherx.presentation.dashboard

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.SecureFlagPolicy
import com.akv.cypherx.presentation.components.AccountCard
import com.akv.cypherx.presentation.components.AccountSearchBar
import com.akv.cypherx.presentation.components.CypherXTopAppBar
import com.akv.cypherx.utils.isScrollingUp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountListScreen(modifier: Modifier = Modifier) {

    val lazyState = rememberLazyListState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true,
        confirmValueChange = { true }
    )
    var showBottomSheet by remember { mutableStateOf(false) }
    var sheetType by remember { mutableStateOf(SheetType.SHOW_DETAILS) }

    Scaffold(
        topBar = {
            Column {
                CypherXTopAppBar(
                    scrollBehavior = scrollBehavior,
                    navigateToSettings = { /*TODO*/ },
                    onFilter = { /*TODO*/ }
                )
            }
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                text = { Text(text = "Add Account") },
                icon = {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Add Account"
                    )
                },
                expanded = lazyState.isScrollingUp().not(),
                onClick = {
                    sheetType = SheetType.ADD_ACCOUNT
                    showBottomSheet = true
                }
            )
        },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)
    ) { innerPadding ->

        LazyColumn(
            state = lazyState,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(horizontal = 12.dp),
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            item { AccountSearchBar() }

            items(100) {
                AccountCard(
                    navigateToAccountDetail = {
                        sheetType = SheetType.SHOW_DETAILS
                        showBottomSheet = true
                    }
                )
            }
        }

        // Bottom Sheet

        if (showBottomSheet) {
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = { showBottomSheet = false },
                properties = ModalBottomSheetProperties(
                    securePolicy = SecureFlagPolicy.SecureOn,
                    isFocusable = true,
                    shouldDismissOnBackPress = true,
                )
            ) {
                when (sheetType) {
                    SheetType.SHOW_DETAILS -> ShowAccountDetailScreen()
                    SheetType.ADD_ACCOUNT -> AddAccountDetailScreen()
                }
            }
        }
    }
}

enum class SheetType {
    SHOW_DETAILS,
    ADD_ACCOUNT
}