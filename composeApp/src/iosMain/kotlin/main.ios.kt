import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.PreComposeApplication

fun MainViewController() = PreComposeApplication {
    BottomPaddingForIOS {
        App()
    }
}


@Composable
private fun BottomPaddingForIOS(content: @Composable () -> Unit) {
    Box(modifier = Modifier.fillMaxSize().padding(bottom = 16.dp)) {
        content()
    }
}