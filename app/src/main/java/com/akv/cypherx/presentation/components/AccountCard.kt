package com.akv.cypherx.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.akv.cypherx.R

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    navigateToAccountDetail: () -> Unit
) {

    ElevatedCard(
        onClick = navigateToAccountDetail,
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        ListItem(
            leadingContent = {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.twitter_3_logo_svgrepo_com),
                        contentDescription = "Twitter",
                        contentScale = ContentScale.Fit
                    )
                }
            },
            headlineContent = { Text(text = "Twitter") },
            supportingContent = { Text(text = "akverma-765") },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Go Forward"
                )
            }
        )
    }
}