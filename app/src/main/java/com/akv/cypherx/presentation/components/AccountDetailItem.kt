package com.akv.cypherx.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow

@Composable
fun AccountDetailItem(
    modifier: Modifier = Modifier,
    overLineText: String,
    headlineText: String,
    onCopyClicked: (() -> Unit)? = null
) {

    ListItem(
        overlineContent = {
            Text(
                text = overLineText,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier.alpha(.5f)
            )
        },
        headlineContent = {
            Text(
                text = headlineText,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        trailingContent = {
            if (onCopyClicked != null) {
                IconButton(
                    onClick = onCopyClicked
                ) {
                    Icon(imageVector = Icons.Filled.ContentCopy, contentDescription = "copy")
                }
            }
        },
        modifier = modifier
    )
}