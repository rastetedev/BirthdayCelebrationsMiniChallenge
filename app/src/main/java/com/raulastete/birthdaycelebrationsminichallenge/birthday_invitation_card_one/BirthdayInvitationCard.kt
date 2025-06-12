package com.raulastete.birthdaycelebrationsminichallenge.birthday_invitation_card_one


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raulastete.birthdaycelebrationsminichallenge.DeviceMode
import com.raulastete.birthdaycelebrationsminichallenge.R
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.BackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.CardBackgroundColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnCardSurfaceColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.OnCardSurfaceWithAlphaColor
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.maliFontFamily
import com.raulastete.birthdaycelebrationsminichallenge.ui.theme.nunitoFontFamily

@Composable
fun BirthdayInvitationCardScreen(deviceMode: DeviceMode) {

    val paddingValues = when (deviceMode) {
        DeviceMode.PhonePortrait -> PaddingValues(horizontal = 16.dp)
        DeviceMode.PhoneLandscape -> PaddingValues(
            horizontal = 100.dp,
            vertical = 20.dp
        )

        DeviceMode.TabletPortrait -> PaddingValues(horizontal = 80.dp)
        else -> PaddingValues(horizontal = 100.dp)
    }

    val detailsPaddingValues = when (deviceMode) {
        DeviceMode.PhonePortrait -> PaddingValues(horizontal = 32.dp)
        DeviceMode.PhoneLandscape -> PaddingValues(
            horizontal = 56.dp,
        )

        DeviceMode.TabletPortrait -> PaddingValues(horizontal = 56.dp)
        else -> PaddingValues(horizontal = 56.dp)
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(color = BackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            modifier = Modifier
                .widthIn(min = 380.dp, max = 640.dp)
                .heightIn(min = 480.dp, max = 800.dp)
                .padding(paddingValues)
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            color = CardBackgroundColor
        ) {
            Box(
                modifier = Modifier
                    .height(IntrinsicSize.Min),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize(),
                    imageVector = ImageVector.vectorResource(R.drawable.birthday_card_decor),
                    contentDescription = null
                )

                Column(
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Spacer(Modifier.weight(1f))

                    Text(
                        "You’re invited!",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp,
                        lineHeight = 43.sp
                    )

                    Text(
                        "Join us for a birthday bash \uD83C\uDF89",
                        fontFamily = maliFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        lineHeight = 28.sp
                    )

                    Spacer(Modifier.height(32.dp))

                    BirthdayInformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(detailsPaddingValues),
                        title = "Date",
                        description = "June 14, 2025"
                    )

                    BirthdayInformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(detailsPaddingValues),
                        title = "Time",
                        description = "3:00 PM"
                    )

                    BirthdayInformationItem(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(detailsPaddingValues),
                        title = "Location",
                        description = "Party Central, 123 Celebration Lane"
                    )

                    Spacer(Modifier.weight(1f))
                    Text(
                        text = "RSVP by June 9",
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        lineHeight = 23.sp,
                        color = OnCardSurfaceWithAlphaColor
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun BirthdayInformationItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String
) {

    Row(modifier = modifier, horizontalArrangement = Arrangement.Center) {
        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 21.sp,
                        color = OnCardSurfaceColor
                    )
                ) { append("${title}: ") }
                withStyle(
                    style = SpanStyle(
                        fontFamily = nunitoFontFamily,
                        fontWeight = FontWeight.Medium,
                        fontSize = 21.sp,
                        color = OnCardSurfaceWithAlphaColor
                    )
                ) { append(description) }
            },
            textAlign = TextAlign.Center
        )
    }
}