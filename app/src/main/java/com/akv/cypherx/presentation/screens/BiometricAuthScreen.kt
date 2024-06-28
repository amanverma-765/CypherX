package com.akv.cypherx.presentation.screens

import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.akv.cypherx.R
import com.akv.cypherx.security.biometric.BiometricPromptManager
import com.akv.cypherx.utils.toast


@Composable
fun BiometricAuthScreen(
    modifier: Modifier = Modifier,
    promptManager: BiometricPromptManager,
    isAuthenticated: (Boolean) -> Unit
) {

    val context = LocalContext.current
    val biometricResult by promptManager.promptResults.collectAsState(
        initial = null
    )
    val enrollLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = {
            println("Activity result: $it")
        }
    )

    LaunchedEffect(biometricResult) {
        if (biometricResult is BiometricPromptManager.BiometricResult.AuthenticationNotSet) {
            if (Build.VERSION.SDK_INT >= 30) {
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                    )
                }
                enrollLauncher.launch(enrollIntent)
            }
        }
    }

    Scaffold() { innerPadding ->

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.fingerprint_icon),
                contentDescription = "FingerPrint",
                tint = MaterialTheme.colorScheme.surfaceTint,
                modifier = Modifier.size(120.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Authentication is needed in order to access your accounts, " +
                        "please authenticate to move further.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .width(350.dp)
                    .padding(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                shape = RoundedCornerShape(12.dp),
                onClick = {
                    promptManager.showBiometricPrompt(
                        title = "Authenticate to CypherX",
                        description = "Authentication is needed in order to access your accounts"
                    )
                }
            ) {
                Text(
                    text = "Authenticate",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
            biometricResult?.let { result ->
                when (result) {
                    is BiometricPromptManager.BiometricResult.AuthenticationError -> {
                        context.toast(result.error)
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationFailed -> {
                        context.toast("Authentication failed")
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationNotSet -> {
                        context.toast("Authentication not set")
                    }

                    BiometricPromptManager.BiometricResult.AuthenticationSuccess -> {
                        isAuthenticated(true)
                        context.toast("Authentication success")
                    }

                    BiometricPromptManager.BiometricResult.FeatureUnavailable -> {
                        context.toast("Feature unavailable")
                    }

                    BiometricPromptManager.BiometricResult.HardwareUnavailable -> {
                        context.toast("Hardware unavailable")
                    }
                }
            }
        }
    }
}
