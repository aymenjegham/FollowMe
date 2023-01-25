package com.angelstudios.followme

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.angelstudios.followme.ui.theme.FollowMeTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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