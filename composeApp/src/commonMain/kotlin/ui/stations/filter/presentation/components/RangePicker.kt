@file:OptIn(ExperimentalMaterialApi::class)

package ui.stations.filter.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import tj.quwatt.quwattapp.SharedRes
import ui.theme.Gray

@Composable
fun RangePicker(
    text: String,
    value: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    bounds: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    reset: Boolean = false
) {

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
                    text = stringResource(SharedRes.strings.price_from, value.start.toInt()),
                    style = MaterialTheme.typography.subtitle1
                )
                Text(
                    text = stringResource(SharedRes.strings.price_to, value.endInclusive.toInt()),
                    style = MaterialTheme.typography.subtitle1
                )
            }
            RangeSlider(
                value = value,
                onValueChange = onValueChange,
                valueRange = bounds
            )
        }
    }
}