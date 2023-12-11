package ui.trips.filter.presentation.components

import androidx.compose.runtime.Composable


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import tj.ham_safar.app.android.theme.Gray
import ui.theme.HamsafarTheme3

@Composable
fun FilterTimePicker(
    modifier: Modifier = Modifier,
    hint: String,
    selectedText: String,
    onTimeChange: (Long) -> Unit,
) {
//    val clockDialogState = rememberUseCaseState()
    HamsafarTheme3 {
//        ClockDialog(
//            state = clockDialogState,
//            selection = ClockSelection.HoursMinutes { hours, minutes ->
//                onTimeChange(LocalTime.of(hours, minutes).toNanoOfDay())
//            }
//        )
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 12.dp
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = hint,
                    style = MaterialTheme.typography.subtitle1,
                    color = Gray
                )
                Row(modifier = Modifier.clickable {
//                    clockDialogState.show()
                }) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = selectedText,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
        }
    }
}
