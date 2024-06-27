package com.akv.cypherx

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.akv.cypherx.presentation.dashboard.AccountListScreen
import com.akv.cypherx.theme.CypherXTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CypherXTheme {
                AccountListScreen()
            }
        }
    }
}