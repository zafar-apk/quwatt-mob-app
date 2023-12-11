package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import core.domain.util.stringResource
import kotlinx.coroutines.delay
import presentatition.SplashScreenEvent
import ui.theme.Yellow

private const val SPLASH_SCREEN_DELAY = 1500L

@Composable
fun SplashScreen(
    onEvent: (SplashScreenEvent) -> Unit
) {
    LaunchedEffect(false) {
        delay(SPLASH_SCREEN_DELAY)
        onEvent(SplashScreenEvent.GoToAuthZone)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = "app_name"),
            style = MaterialTheme.typography.h1,
            color = Color.White
        )
    }
}

//@Preview
//@Composable
//fun SplashScreenPreview() {
//    HamSafarTheme {
//        SplashScreen(onEvent = {})
//    }
//}