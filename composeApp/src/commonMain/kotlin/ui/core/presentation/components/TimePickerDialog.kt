@file:OptIn(ExperimentalMaterial3Api::class)

package ui.core.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TextButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.domain.util.stringResource
import ui.theme.BackgroundGray
import ui.theme.Blue
import ui.theme.LightGray
import tj.yakroh.yakrohapp.SharedRes

@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onTimePicked: (String) -> Unit
) {
    val timePickerState = rememberTimePickerState()

    Dialog(onDismissRequest = onDismissRequest) {
        Column(
            modifier = Modifier.background(
                color = BackgroundGray,
                RoundedCornerShape(16.dp)
            )
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timePickerState,
                colors = TimePickerDefaults.colors(
                    clockDialColor = LightGray,
                    timeSelectorUnselectedContainerColor = LightGray,
                    timeSelectorSelectedContainerColor = MaterialTheme.colors.primary,
                    timeSelectorSelectedContentColor = MaterialTheme.colors.onPrimary,
                    selectorColor = MaterialTheme.colors.primary
                )
            )

            Spacer(modifier = Modifier.size(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = getFormattedTime(timePickerState),
                    style = MaterialTheme.typography.subtitle2,
                )

                Spacer(Modifier.weight(1F))

                TextButton(
                    onClick = {
                        onTimePicked(getFormattedTime(timePickerState))
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = stringResource(SharedRes.strings.select),
                        style = MaterialTheme.typography.subtitle2,
                        color = MaterialTheme.colors.secondary
                    )
                }
            }
        }
    }

}

fun getFormattedTime(timePickerState: TimePickerState): String {
    return buildString {
        append(if (timePickerState.hour > 9) timePickerState.hour else "0${timePickerState.hour}")
        append(":")
        append(if (timePickerState.minute > 9) timePickerState.minute else "0${timePickerState.minute}")
    }
}
