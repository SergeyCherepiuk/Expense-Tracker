package com.example.expensetracker.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.example.expensetracker.R

val SourceSans3 = FontFamily(
    Font(R.font.sourcesans3_black, FontWeight.Black),
    Font(R.font.sourcesans3_blackitalic, FontWeight.Black, FontStyle.Italic),
    Font(R.font.sourcesans3_bold, FontWeight.Bold),
    Font(R.font.sourcesans3_bolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.sourcesans3_extrabold, FontWeight.ExtraBold),
    Font(R.font.sourcesans3_extrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.sourcesans3_extralight, FontWeight.ExtraLight),
    Font(R.font.sourcesans3_extralightitalic, FontWeight.ExtraLight, FontStyle.Italic),
    Font(R.font.sourcesans3_italic, FontWeight.Normal, FontStyle.Italic),
    Font(R.font.sourcesans3_light, FontWeight.Light),
    Font(R.font.sourcesans3_lightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.sourcesans3_medium, FontWeight.Medium),
    Font(R.font.sourcesans3_mediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.sourcesans3_regular, FontWeight.Normal),
    Font(R.font.sourcesans3_semibold, FontWeight.SemiBold),
    Font(R.font.sourcesans3_semibolditalic, FontWeight.SemiBold, FontStyle.Italic),
)

val NotoEmoji = FontFamily(
    Font(R.font.notoemoji_bold, FontWeight.Bold),
    Font(R.font.notoemoji_light, FontWeight.Light),
    Font(R.font.notoemoji_medium, FontWeight.Medium),
    Font(R.font.notoemoji_regular, FontWeight.Normal),
    Font(R.font.notoemoji_semibold, FontWeight.SemiBold),
)

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = SourceSans3,
        fontWeight = FontWeight.Normal
    ),
    h2 = TextStyle(
        fontFamily = SourceSans3,
        fontWeight = FontWeight.Medium
    ),
    h3 = TextStyle(
        fontFamily = SourceSans3,
        fontWeight = FontWeight.SemiBold
    ),
)