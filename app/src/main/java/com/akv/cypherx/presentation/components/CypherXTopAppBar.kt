package com.akv.cypherx.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CypherXTopAppBar(
    modifier: Modifier = Modifier,
    navigateToSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onFilter: () -> Unit
) {

    TopAppBar(
        scrollBehavior = scrollBehavior,
        title = { CypherXLogo() },
        actions = {
            IconButton(onClick = onFilter) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More"
                )
            }
            IconButton(onClick = navigateToSettings) {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = "Settings"
                )
            }
        },
        modifier = modifier
    )

}