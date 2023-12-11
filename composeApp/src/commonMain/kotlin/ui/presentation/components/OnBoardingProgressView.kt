package tj.ham_safar.app.android.on_boarding.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import tj.ham_safar.app.android.theme.Blue
import ui.theme.HamSafarTheme

private const val PROGRESS_ITEM_SPACE_DP = 31
private const val MAX_STAGES = 3

@Composable
fun OnBoardingProgressView(
    modifier: Modifier = Modifier,
    stage: Int,
    maxStages: Int = MAX_STAGES
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Divider(
            modifier = Modifier.width((PROGRESS_ITEM_SPACE_DP * maxStages).dp),
            color = White
        )
        LazyRow {
            items(maxStages) {
                val current = it + 1
                ProgressItem(isSelected = stage == current)
                if (current != maxStages) {
                    Spacer(modifier = Modifier.width(PROGRESS_ITEM_SPACE_DP.dp))
                }
            }
        }
    }
}

@Composable
private fun ProgressItem(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(13.dp)
            .clip(CircleShape)
            .background(
                color = if (isSelected) Blue else White
            )
    )
}

//@Preview
//@Composable
//fun OnBoardingProgressViewPreview() {
//    HamSafarTheme {
//        OnBoardingProgressView(stage = 1, maxStages = 3)
//    }
//}