package com.akv.cypherx.presentation.components

import android.content.ContentValues.TAG
import android.util.Log
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.exclude
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountSearchBar(modifier: Modifier = Modifier) {

    SearchBar(
        query = "",
        onQueryChange = {},
        onSearch = {},
        active = false,
        onActiveChange = { state ->
            Log.e(TAG, "AccountSearchBar: $state")
        },
        trailingIcon = {
            IconButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.FilterList,
                    contentDescription = "More"
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "Search"
            )
        },
        placeholder = { Text("Search") },
        windowInsets = SearchBarDefaults.windowInsets.exclude(WindowInsets.systemBars),
        modifier = modifier
            .fillMaxWidth()
    ) {}

}