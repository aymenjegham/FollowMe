package com.angelstudios.followme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.angelstudios.followme.ui.theme.FollowMeTheme

class MainActivity : ComponentActivity() {

    private var keepSplashScreenOpened = true

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().setKeepOnScreenCondition {
            keepSplashScreenOpened
        }

        super.onCreate(savedInstanceState)

        setContent {
            FollowMeTheme {
                Navigation() {
                    keepSplashScreenOpened = false
                }
            }
        }
    }
}