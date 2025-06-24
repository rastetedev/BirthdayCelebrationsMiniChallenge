package com.raulastete.birthdaycelebrationsminichallenge.countdown_to_cake_third

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BirthdayCelebrationsMiniChallengeTheme

class CountdownToCakeActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(BackgroundColor.value.toInt())
        )
        setContent {
            BirthdayCelebrationsMiniChallengeTheme {
                CountdownToCakeScreen()
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 0)
            }
        }
    }
}