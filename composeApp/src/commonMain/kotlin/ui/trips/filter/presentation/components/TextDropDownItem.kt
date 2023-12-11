package ui.trips.filter.presentation.components

import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextDropDownItem(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit,
) {
    DropdownMenuItem(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text
        )
    }

}