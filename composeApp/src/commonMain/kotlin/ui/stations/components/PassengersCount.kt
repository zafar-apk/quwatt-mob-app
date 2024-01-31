package tj.ham_safar.app.android.trips.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import ui.theme.Blue

@Composable
fun PassengersCount(
    modifier: Modifier = Modifier,
    progress: Float,
    text: String
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(progress = progress, color = Blue)
        Text(text = text, style = MaterialTheme.typography.body1.copy(fontSize = 10.sp))
    }
}