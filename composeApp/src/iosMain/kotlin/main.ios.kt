import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import core.domain.util.HapticFeedback
import moe.tlaster.precompose.PreComposeApplication

fun MainViewController() = PreComposeApplication {
    BottomPaddingForIOS {
        CompositionLocalProvider(LocalHapticFeedback provides HapticFeedback()) {
            App()
        }
    }
}


@Composable
private fun BottomPaddingForIOS(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)) {
        content()
    }
}