package com.raulastete.birthdaycelebrationsminichallenge.birthday_invitation_card_one

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.platform.LocalConfiguration
import com.raulastete.birthdaycelebrationsminichallenge.DeviceMode
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BirthdayCelebrationsMiniChallengeTheme

class BirthdayInvitationCardActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val widthSizeClass = calculateWindowSizeClass(this).widthSizeClass
            val heightSizeClass = calculateWindowSizeClass(this).heightSizeClass

            val configuration = LocalConfiguration.current

            val deviceMode = when {
                widthSizeClass == WindowWidthSizeClass.Compact &&
                        configuration.orientation == ORIENTATION_PORTRAIT -> {
                    DeviceMode.PhonePortrait
                }

                heightSizeClass == WindowHeightSizeClass.Compact &&
                        configuration.orientation == ORIENTATION_LANDSCAPE -> {
                    DeviceMode.PhoneLandscape
                }

                configuration.orientation == ORIENTATION_PORTRAIT -> {
                    DeviceMode.TabletPortrait
                }

                else -> {
                    DeviceMode.TabletLandscape
                }
            }


            BirthdayCelebrationsMiniChallengeTheme {
                BirthdayInvitationCardScreen(deviceMode)
            }
        }

    }
}