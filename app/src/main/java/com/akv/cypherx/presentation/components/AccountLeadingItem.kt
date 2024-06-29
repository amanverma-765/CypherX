package com.akv.cypherx.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akv.cypherx.utils.generateRandomColor

@Composable
fun AccountLeadingItem(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    title: String
) {

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {

        AnimatedVisibility(
            visible = title.isNotBlank(),
            exit = slideOutVertically { it },
            enter = slideInVertically { it }
        ) {

            val isDarkMode = isSystemInDarkTheme()
            val firstChar = remember(title.isNotBlank()) { title }
            val bgColor = remember(title.isNotBlank()) { generateRandomColor(isDarkMode) }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(16.dp))
                    .background(bgColor)
            ) {

                Text(
                    text = firstChar.ifBlank { " " }[0].uppercase(),
                    fontWeight = FontWeight.Bold,
                    style = textStyle,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )

            }
        }
    }
}