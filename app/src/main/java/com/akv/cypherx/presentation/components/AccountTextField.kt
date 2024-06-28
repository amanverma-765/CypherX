package com.akv.cypherx.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun AccountTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String,
    isPassword: Boolean = false,
    leadingIcon: @Composable () -> Unit,
    isError: Boolean,
    supportingText: @Composable (() -> Unit)? = null,
    onTextChange: (String) -> Unit
) {

    var showPassword by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        value = text,
        onValueChange = onTextChange,
        singleLine = true,
        label = { Text(text = label) },
        leadingIcon = leadingIcon,
        trailingIcon = {
            if (isPassword) {
                Icon(
                    if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                    contentDescription = if (showPassword) "Show Password" else "Hide Password",
                    modifier = Modifier
                        .clip(CircleShape)
                        .clickable { showPassword = !showPassword }
                )
            }
        },
        visualTransformation = if (isPassword) {
            if (showPassword) VisualTransformation.None else PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        isError = isError,
        supportingText = supportingText,
        modifier = modifier.fillMaxWidth()
    )
}