package ui.trips.filter.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import ui.theme.Gray

@Composable
fun RangePicker(
    modifier: Modifier = Modifier,
    reset: Boolean = false,
    text: String,
    lowerBound: Int,
    upperBound: Int,
    onLowerBoundChange: (Int) -> Unit,
    onUpperBoundChange: (Int) -> Unit,
) {
    val steps = (0..500).step(10).toList()

    Card(modifier = modifier.fillMaxWidth(), elevation = 12.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = text,
                style = MaterialTheme.typography.subtitle1,
                color = Gray
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = "price_from", listOf(lowerBound)),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = stringResource(id = "price_to", listOf(upperBound)),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            // kinda f-up
//            LabeledRangeSlider(
//                modifier = Modifier.fillMaxWidth(),
//                selectedLowerBound = lowerBound,
//                selectedUpperBound = upperBound,
//                steps = steps,
//                reset = reset,
//                onRangeChanged = { lower, upper ->
//                    onLowerBoundChange(lower)
//                    onUpperBoundChange(upper)
//                }
//            )
        }

    }
}