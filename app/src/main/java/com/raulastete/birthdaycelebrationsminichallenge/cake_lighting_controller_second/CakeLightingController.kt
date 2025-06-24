package com.raulastete.birthdaycelebrationsminichallenge.cake_lighting_controller_second

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.raulastete.birthdaycelebrationsminichallenge.R
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnSurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.SurfaceHigherColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.maliFontFamily
import kotlinx.coroutines.delay

@Composable
fun CakeLightingControllerScreen() {

    val candles = remember {
        mutableStateMapOf<String, Boolean>(
            "candleTopStart" to true,
            "candleTopEnd" to true,
            "candleCenterStart" to true,
            "candleCenterCenter" to true,
            "candleCenterEnd" to true,
            "candleBottomStart" to true,
            "candleBottomEnd" to true,
        )
    }

    var isAllCandlesOn = remember { mutableStateOf(true) }
    var isAllCandlesOff = remember { mutableStateOf(false) }
    var canTurnOn by remember { mutableStateOf(true) }

    LaunchedEffect(candles.values.toList()) {
        isAllCandlesOn.value = candles.all { it.value == true }
        isAllCandlesOff.value = candles.all { it.value == false }
        canTurnOn = candles.any { it.value == true }
    }

    fun turnCandlesOn() {
        candles.keys.forEach {
            candles[it] = true
        }
    }

    val titleLightsOn by remember { mutableStateOf("Ready for cake \uD83C\uDF89") }
    val titleLightsOff by remember { mutableStateOf("Make a wish... \uD83D\uDCA8") }

    Scaffold(Modifier.fillMaxSize(), containerColor = BackgroundColor) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            AnimatedContent(
                when {
                    isAllCandlesOn.value -> titleLightsOn
                    isAllCandlesOff.value -> titleLightsOff
                    else -> ""
                }
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 74.dp),
                    text = it,
                    fontFamily = maliFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 33.sp,
                    color = SurfaceColor,
                    textAlign = TextAlign.Center
                )
            }

            ConstraintLayout(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {

                val (
                    candleTopStart,
                    candleTopEnd,
                    candleCenterStart,
                    candleCenterCenter,
                    candleCenterEnd,
                    candleBottomStart,
                    candleBottomEnd,
                ) = createRefs()

                Candle(
                    modifier = Modifier.constrainAs(candleTopStart) {
                        start.linkTo(candleCenterStart.end)
                        end.linkTo(candleCenterCenter.start)
                        bottom.linkTo(candleCenterCenter.top, margin = (-100).dp)
                    },
                    onTap = {
                        candles["candleTopStart"] = false
                    },
                    isOn = candles["candleTopStart"]!!,
                    candle = R.drawable.candle_top_start,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleTopStart"] = true
                        }
                    }
                )

                Candle(
                    modifier = Modifier.constrainAs(candleTopEnd) {
                        start.linkTo(candleCenterCenter.end)
                        end.linkTo(candleCenterEnd.start)
                        bottom.linkTo(candleTopStart.bottom)
                        top.linkTo(candleTopStart.top)
                    },
                    onTap = {
                        candles["candleTopEnd"] = false
                    },
                    isOn = candles["candleTopEnd"]!!,
                    candle = R.drawable.candle_top_end,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleTopEnd"] = true
                        }
                    },
                )

                Candle(
                    modifier = Modifier.constrainAs(candleCenterStart) {
                        top.linkTo(candleCenterCenter.top)
                        bottom.linkTo(candleCenterCenter.bottom)
                        start.linkTo(parent.start, margin = 24.dp)
                    },
                    onTap = {
                        candles["candleCenterStart"] = false
                    },
                    isOn = candles["candleCenterStart"]!!,
                    candle = R.drawable.candle_center_start,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleCenterStart"] = true
                        }
                    }
                )

                Candle(
                    modifier = Modifier.constrainAs(candleCenterCenter) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    onTap = {
                        candles["candleCenterCenter"] = false
                    },
                    isOn = candles["candleCenterCenter"]!!,
                    candle = R.drawable.candle_center_center,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleCenterCenter"] = true
                        }
                    },

                    )

                Candle(
                    modifier = Modifier.constrainAs(candleCenterEnd) {
                        top.linkTo(candleCenterCenter.top)
                        bottom.linkTo(candleCenterCenter.bottom)
                        end.linkTo(parent.end, margin = 24.dp)
                    },
                    onTap = {
                        candles["candleCenterEnd"] = false
                    },
                    isOn = candles["candleCenterEnd"]!!,
                    candle = R.drawable.candle_center_end,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleCenterEnd"] = true
                        }
                    }
                )

                Candle(
                    modifier = Modifier.constrainAs(candleBottomStart) {
                        top.linkTo(candleCenterCenter.bottom, margin = (-100).dp)
                        start.linkTo(candleTopStart.start)
                        end.linkTo(candleTopStart.end)
                    },
                    onTap = {
                        candles["candleBottomStart"] = false
                    },
                    isOn = candles["candleBottomStart"]!!,
                    candle = R.drawable.candle_bottom_start,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleBottomStart"] = true
                        }
                    },
                )

                Candle(
                    modifier = Modifier.constrainAs(candleBottomEnd) {
                        top.linkTo(candleBottomStart.top)
                        bottom.linkTo(candleBottomStart.bottom)
                        start.linkTo(candleTopEnd.start)
                        end.linkTo(candleTopEnd.end)
                    },
                    onTap = {
                        candles["candleBottomEnd"] = false
                    },
                    isOn = candles["candleBottomEnd"]!!,
                    candle = R.drawable.candle_bottom_end,
                    turnCandleOn = {
                        if (canTurnOn) {
                            candles["candleBottomEnd"] = true
                        }
                    },
                )
            }

            AnimatedVisibility(
                isAllCandlesOn.value.not()
            ) {
                Button(
                    modifier = Modifier.padding(bottom = 36.dp),
                    onClick = {
                        turnCandlesOn()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = SurfaceHigherColor,
                        contentColor = OnSurfaceColor
                    ),
                    contentPadding = PaddingValues(horizontal = 28.dp, vertical = 8.dp),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(
                        "Light all candles",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}

@Composable
fun Candle(
    modifier: Modifier = Modifier,
    @DrawableRes candle: Int,
    isOn: Boolean,
    turnCandleOn: () -> Unit,
    onTap: (isOn: Boolean) -> Unit
) {

    LaunchedEffect(isOn) {
        if (isOn.not()) {
            delay(4000)
            turnCandleOn()
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (isOn) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.candle_state_on),
                contentDescription = null,
            )
        } else {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.candle_state_off),
                contentDescription = null,
            )
        }

        Image(
            modifier = Modifier.clickable {
                onTap(isOn)
            },
            imageVector = ImageVector.vectorResource(candle),
            contentDescription = null,
        )
    }

}