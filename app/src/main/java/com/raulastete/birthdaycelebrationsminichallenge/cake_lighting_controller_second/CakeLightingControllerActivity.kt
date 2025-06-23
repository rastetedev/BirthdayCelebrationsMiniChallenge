package com.raulastete.birthdaycelebrationsminichallenge.cake_lighting_controller_second

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BirthdayCelebrationsMiniChallengeTheme

class CakeLightingControllerActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(BackgroundColor.value.toInt())
        )
        setContent {

            BirthdayCelebrationsMiniChallengeTheme {
                CakeLightingControllerScreen()
            }
        }

    }
}