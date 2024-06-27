package com.akv.cypherx.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akv.cypherx.R

@Composable
fun AccountTextField(modifier: Modifier = Modifier) {

    OutlinedTextField(
        value = "",
        onValueChange = {},
        singleLine = true,
        label = { Text(text = "Account Name") },
        leadingIcon = {
            Box(modifier = Modifier.size(30.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.twitter_3_logo_svgrepo_com),
                    contentDescription = "",
                )
            }
        },
        supportingText = { Text(text = "Password must be more than 8 characters") },
        modifier = modifier.fillMaxWidth()
    )
}