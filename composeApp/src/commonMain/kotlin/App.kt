
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import core.presentation.LocalPaddings
import core.presentation.Paddings
import org.koin.compose.KoinContext
import ui.root.QuWattRoot
import ui.theme.AppTheme

@Composable
fun App() {
    KoinContext {
        AppTheme {
            CompositionLocalProvider(LocalPaddings provides Paddings()) {
                Surface {
                    QuWattRoot()
                }
            }
        }
    }
}