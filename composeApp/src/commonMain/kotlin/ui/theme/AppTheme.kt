package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTheme
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import io.github.alexzhirkevich.cupertino.adaptive.Theme
import io.github.alexzhirkevich.cupertino.theme.CupertinoTheme
import io.github.alexzhirkevich.cupertino.theme.darkColorScheme
import io.github.alexzhirkevich.cupertino.theme.lightColorScheme

@Composable
fun QuWattTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val typography = Typography(
        h1 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp
        ),
        h2 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            color = TextBlack
        ),
        h3 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        subtitle1 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp
        ),
        body1 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        body2 = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
        caption = TextStyle(
//            fontFamily = GtEestiPro,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        ),
    )
    val shapes = Shapes(
        small = RoundedCornerShape(4.dp),
        medium = RoundedCornerShape(4.dp),
        large = RoundedCornerShape(0.dp)
    )

    MaterialTheme(
        colors = lightColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    theme: Theme = determineTheme(),
    content: @Composable () -> Unit
) {
    AdaptiveTheme(
        material = {
            androidx.compose.material3.MaterialTheme(
                colorScheme = if (useDarkTheme) {
                    androidx.compose.material3.darkColorScheme()
                } else {
                    androidx.compose.material3.lightColorScheme()
                },
                content = it
            )
        },
        cupertino = {
            CupertinoTheme(
                colorScheme = if (useDarkTheme) {
                    darkColorScheme()
                } else {
                    lightColorScheme()
                },
                content = it
            )

        },
        target = theme,
        content = content
    )
}

expect fun determineTheme(): Theme

//val SfProText = FontFamily(
//    Font(
//        resId = R.font.sf_pro_text_regular,
//        weight = FontWeight.Normal
//    ),
//    Font(
//        resId = R.font.sf_pro_text_medium,
//        weight = FontWeight.Medium
//    ),
//    Font(
//        resId = R.font.sf_pro_text_bold,
//        weight = FontWeight.Bold
//    ),
//)
//val GtEestiPro = FontFamily(
//    Font(
//        resId = R.font.gteestiprodisplay_light,
//        weight = FontWeight.Light
//    ),
//    Font(
//        resId = R.font.gteestiprodisplay_thin,
//        weight = FontWeight.Thin
//    ),
//    Font(
//        resId = R.font.gteestiprodisplay_regular,
//        weight = FontWeight.Normal
//    ),
//    Font(
//        resId = R.font.gteestiprodisplay_medium,
//        weight = FontWeight.Medium
//    ),
//    Font(
//        resId = R.font.gteestiprodisplay_bold,
//        weight = FontWeight.Bold
//    ),
//    Font(
//        resId = R.font.gteestiprodisplay_ultrabold,
//        weight = FontWeight.ExtraBold
//    )
//)

val propertyText = TextStyle(
//    fontFamily = GtEestiPro,
    fontSize = 18.sp,
    fontWeight = FontWeight(400),
    color = Color.Black,
)
val labelText = TextStyle(
//    fontFamily = GtEestiPro,
    fontSize = 14.sp,
    color = Gray,
    fontWeight = FontWeight(400),
)