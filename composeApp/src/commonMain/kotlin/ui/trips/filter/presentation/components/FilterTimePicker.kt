@file:OptIn(ExperimentalMaterial3Api::class)

package ui.trips.filter.presentation.components


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.theme.Gray

@Composable
fun FilterTimePicker(
    modifier: Modifier = Modifier,
    hint: String,
    selectedText: String,
    onOpenTimePicker: () -> Unit,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = 12.dp
    ) {
        Column(
            modifier = Modifier.clickable { onOpenTimePicker() }
                .padding(8.dp)
        ) {
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
