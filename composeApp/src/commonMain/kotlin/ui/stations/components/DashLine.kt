package tj.ham_safar.app.android.trips.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.unit.dp

@Composable
fun DashLine(modifier: Modifier = Modifier) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    Canvas(
        modifier
            .width(2.dp)
            .height(55.dp)
    ) {

        drawLine(
            color = Color.Black,
            start = Offset(0f, 0f),
            end = Offset(0F, size.height),
            pathEffect = pathEffect
        )
    }
}