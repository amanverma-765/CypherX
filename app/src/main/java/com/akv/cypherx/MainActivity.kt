package com.akv.cypherx

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import com.akv.cypherx.presentation.screens.AccountListScreen
import com.akv.cypherx.presentation.screens.BiometricAuthScreen
import com.akv.cypherx.security.biometric.BiometricPromptManager
import com.akv.cypherx.theme.CypherXTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
class MainActivity : AppCompatActivity() {

    private val promptManager by lazy { BiometricPromptManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CypherXTheme {
                KoinAndroidContext {

                    var isAuthenticated by remember { mutableStateOf(false) }

                    if (isAuthenticated) {
                        BottomSheetNavigator(
                            sheetBackgroundColor = MaterialTheme.colorScheme.surface,
                            sheetContentColor = MaterialTheme.colorScheme.onSurface,
                            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
                            sheetGesturesEnabled = false,
                        ) {
                            Navigator(AccountListScreen())
                        }
                    } else {
                        BiometricAuthScreen(promptManager = promptManager) {
                            isAuthenticated = it
                        }
                    }
                }
            }
        }
    }
}