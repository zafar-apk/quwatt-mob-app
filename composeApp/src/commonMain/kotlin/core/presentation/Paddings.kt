package core.presentation

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalPaddings = compositionLocalOf { Paddings() }

data class Paddings(
    val mini: Dp = 4.dp,
    val small: Dp = mini * 2,
    val medium: Dp = small * 2,
    val large: Dp = medium + small
)