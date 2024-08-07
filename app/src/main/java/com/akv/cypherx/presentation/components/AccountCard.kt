package com.akv.cypherx.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowForwardIos
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.akv.cypherx.R
import com.akv.cypherx.domain.model.AccountData
import com.akv.cypherx.utils.Constants.LOGO_FETCH_URL

@Composable
fun AccountCard(
    modifier: Modifier = Modifier,
    accountData: AccountData,
    navigateToAccountDetail: (Int) -> Unit
) {

    ElevatedCard(
        onClick = { accountData.id?.let { navigateToAccountDetail(it) } },
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {
        ListItem(
            leadingContent = {
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.aspectRatio(1f)
                ) {
                    AccountLeadingItem(
                        title = accountData.accountName,
                        websiteUrl = accountData.websiteUrl,
                        textStyle = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.size(50.dp)
                    )
                }
            },
            headlineContent = {
                Text(
                    text = accountData.accountName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            supportingContent = {
                Text(
                    text = accountData.accountUsername,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            },
            trailingContent = {
                Icon(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowForwardIos,
                    contentDescription = "Go Forward"
                )
            }
        )
    }
}