package com.raulastete.birthdaycelebrationsminichallenge.countdown_to_cake_third

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.app.NotificationCompat
import com.raulastete.birthdaycelebrationsminichallenge.R
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.ErrorColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnSurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceHigherColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.maliFontFamily
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CountdownToCakeScreen() {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var counterValue by remember { mutableIntStateOf(5) }
    var isCounterInProgress by remember { mutableStateOf(false) }

    var countdownJob: Job? = null

    fun showNotification() {
        val channelId = "countdown_channel"
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "birthday_counter",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Yuhuu!")
            .setContentText("It's cake time 🎂!")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setSmallIcon(R.drawable.cake)
            .build()

        notificationManager.notify(1, notification)
    }

    fun startCountdown() {
        isCounterInProgress = true
        countdownJob = scope.launch {
            while (counterValue >= 2) {
                delay(1000)
                counterValue -= 1
                if (counterValue == 1) {
                    showNotification()
                }
            }
        }
    }

    fun stopCountdown() {
        countdownJob?.cancel()
        isCounterInProgress = false
        counterValue = 5
    }

    Scaffold(containerColor = BackgroundColor) {

        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (decor, cake, startCounterButton, cancelButton, counter, candle) = createRefs()

            Image(
                modifier = Modifier.constrainAs(decor) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                imageVector = ImageVector.vectorResource(R.drawable.countdown_decor),
                contentDescription = null
            )

            Image(
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .constrainAs(cake) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        bottom.linkTo(parent.bottom)
                    }
                    .absoluteOffset(y = 110.dp),
                imageVector = ImageVector.vectorResource(R.drawable.cake),
                contentDescription = null
            )

            AnimatedVisibility(
                visible = isCounterInProgress.not(),
                modifier = Modifier.constrainAs(startCounterButton) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom, margin = 200.dp)
                }
            ) {
                Button(
                    onClick = {
                        startCountdown()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceColor,
                        contentColor = OnSurfaceColor
                    )
                ) {
                    Text(
                        "Count to Cake!",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }

            AnimatedVisibility(
                visible = isCounterInProgress,
                modifier = Modifier.constrainAs(candle) {
                    bottom.linkTo(counter.bottom, margin = 270.dp)
                    top.linkTo(counter.top)
                    start.linkTo(counter.start)
                    end.linkTo(counter.end)
                }
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(R.drawable.counter_candle),
                    contentDescription = null
                )
            }

            AnimatedVisibility(
                enter = slideInVertically(),
                exit = slideOutVertically() + fadeOut(),
                visible = isCounterInProgress,
                modifier = Modifier.constrainAs(counter) {
                    start.linkTo(cake.start)
                    end.linkTo(cake.end)
                    bottom.linkTo(parent.bottom, margin = 140.dp)
                    top.linkTo(parent.top)

                }
            ) {
                Text(
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                    text = counterValue.toString(),
                    color = SurfaceHigherColor,
                    fontFamily = maliFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 360.sp
                )
            }

            AnimatedVisibility(
                visible = isCounterInProgress,
                modifier = Modifier
                    .constrainAs(cancelButton) {
                        bottom.linkTo(parent.bottom, margin = 24.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            ) {
                Button(
                    onClick = {
                        stopCountdown()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceColor,
                        contentColor = ErrorColor
                    )
                ) {
                    Text(
                        "Cancel",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

