package tj.ham_safar.app.android.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import ui.theme.BackgroundGray

@Composable
fun Loader() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.7F)
            .background(BackgroundGray)
            .clickable { /* no-op */ },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

//@Preview
//@Composable
//fun LoaderPreview() {
//    HamSafarTheme {
//        Loader()
//    }
//}