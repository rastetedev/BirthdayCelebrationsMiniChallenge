package com.raulastete.birthdaycelebrationsminichallenge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.raulastete.birthdaycelebrationsminichallenge.R

val maliFontFamily = FontFamily(
    Font(
        resId = R.font.mali_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.mali_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.mali_medium,
        weight = FontWeight.Medium,
    )
)

val nunitoFontFamily = FontFamily(
    Font(
        resId = R.font.nunito_medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resId = R.font.nunito_semibold,
        weight = FontWeight.SemiBold,
    ),
    Font(
        resId = R.font.nunito_bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resId = R.font.nunito_regular,
        weight = FontWeight.Normal,
    )
)

// Set of Material typography styles to start with
val Typography = Typography()