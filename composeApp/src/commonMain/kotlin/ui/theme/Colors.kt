package ui.theme

import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color
import core.presentation.Colors

val BackgroundGray = Color(Colors.BackgroundGray)
val Primary = Color(Colors.Primary)
val GrayDuoTone = Color(Colors.DuoToneGray)
val Blue = Color(Colors.Secondary)
val DarkBlue = Color(Colors.DarkBlue)
val Red = Color(Colors.Red)
val Gray = Color(Colors.Gray)
val PrimaryGray = Color(Colors.PrimaryGray)
val GrayGainsboro = Color(Colors.GrayGainsboro)
val GrayAlt = Color(Colors.GrayAlt)
val LightGray = Color(Colors.LightGray)
val TextBlack = Color.Black

val lightColors = lightColors(
    primary = Primary,
    background = BackgroundGray,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack,
    secondary = Blue
)
val lightColorScheme = lightColors(
    primary = Primary,
    background = BackgroundGray,
    onPrimary = Color.White,
    onBackground = TextBlack,
    surface = Color.White,
    onSurface = TextBlack

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

// TODO: I don't have time for making dark theme
//val darkColors = darkColors(
//    primary = AccentViolet,
//    background = DarkGrey,
//    onPrimary = Color.White,
//    onBackground = Color.White,
//    surface = DarkGrey,
//    onSurface = Color.White
//)