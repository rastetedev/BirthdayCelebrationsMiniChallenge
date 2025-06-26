package com.raulastete.birthdaycelebrationsminichallenge

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.raulastete.birthdaycelebrationsminichallenge.birthday_invitation_card_one.BirthdayInvitationCardActivity
import com.raulastete.birthdaycelebrationsminichallenge.cake_lighting_controller_second.CakeLightingControllerActivity
import com.raulastete.birthdaycelebrationsminichallenge.countdown_to_cake_third.CountdownToCakeActivity
import com.raulastete.birthdaycelebrationsminichallenge.party_host_dashboard_fourth.PartyHostDashboardActivity
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BirthdayCelebrationsMiniChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            BirthdayCelebrationsMiniChallengeTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Button(onClick = {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                BirthdayInvitationCardActivity::class.java
                            )
                        )
                    }) {
                        Text(
                            "Mini challenge #1 - Birthday Invitation Card",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                CakeLightingControllerActivity::class.java
                            )
                        )
                    }) {
                        Text(
                            "Mini challenge #2 - Cake Lighting Controller",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                CountdownToCakeActivity::class.java
                            )
                        )
                    }) {
                        Text(
                            "Mini challenge #3 - Countdown to Cake",
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    Button(onClick = {
                        startActivity(
                            Intent(
                                this@MainActivity,
                                PartyHostDashboardActivity::class.java
                            )
                        )
                    }) {
                        Text(
                            "Mini challenge #4 - Party Host Dashboard",
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}

sealed interface DeviceMode {
    object PhonePortrait : DeviceMode
    object PhoneLandscape : DeviceMode
    object TabletPortrait : DeviceMode
    object TabletLandscape : DeviceMode
}
