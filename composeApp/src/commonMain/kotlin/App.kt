import androidx.compose.runtime.Composable
import org.koin.compose.KoinContext
import ui.root.HamSafarRoot
import ui.theme.HamSafarTheme

@Composable
fun App() {
    KoinContext {
        HamSafarTheme {
            HamSafarRoot()
        }
    }
}