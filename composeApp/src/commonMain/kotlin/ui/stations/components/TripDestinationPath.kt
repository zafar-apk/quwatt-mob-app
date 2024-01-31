package tj.ham_safar.app.android.trips.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

fun DrawScope.TripDestinationPath() {
    val path = Path()
    val startingPointXOfPath = 0f + 14.dp.toPx()
    val startingPointYOfPath = 0f + 22.dp.toPx()
    val endingPointXOfPath = size.width - 14.dp.toPx()
    val endingPointYOfPath = size.height - 22.dp.toPx()
    val x1SizeRatio = .002f
    val x2SizeRatio = .9f
    val filledIntervalHeight = 7.5F
    val emptyIntervalHeight = 10f
    val intervals = floatArrayOf(
        filledIntervalHeight,
        emptyIntervalHeight
    )
    path.reset()
    path.moveTo(startingPointXOfPath, startingPointYOfPath)
    path.cubicTo(
        x1 = size.width * x1SizeRatio,
        y1 = size.height,
        x2 = size.width * x2SizeRatio,
        y2 = 0f,
        x3 = endingPointXOfPath,
        y3 = endingPointYOfPath
    )
    drawPath(
        color = Color.Black,
        path = path,
        style = Stroke(
            width = 1.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(
                intervals = intervals
            )
        )
    )
}