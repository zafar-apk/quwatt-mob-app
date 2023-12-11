package ui.trips.filter.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ui.theme.Gray

@Composable
fun TextDropDown(
    hint: String,
    selectedText: String,
    isDropDownOpened: Boolean,
    options: List<String>,
    onOpenDropDown: () -> Unit,
    onSelectOption: (String) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    optionsModifier: Modifier = Modifier,
    elevation: Dp = 12.dp
) {
    Card(
        modifier = modifier,
        elevation = elevation
    ) {
        Row(
            modifier = Modifier
                .clickable { onOpenDropDown() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (hint.isNotEmpty()) {
                Text(
                    text = hint,
                    color = Gray,
                    style = MaterialTheme.typography.subtitle1
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = selectedText,
                    color = MaterialTheme.colors.onBackground,
                    style = MaterialTheme.typography.subtitle1
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null
                )
                DropdownMenu(
                    modifier = optionsModifier,
                    expanded = isDropDownOpened,
                    onDismissRequest = onDismiss,
                ) {
                    options.forEach { option ->
                        key(option) {
                            TextDropDownItem(
                                modifier = Modifier.fillMaxWidth(),
                                text = option,
                                onClick = {
                                    onSelectOption(option)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

//@Preview
//@Composable
//private fun TextDropDownPreview() {
//    HamSafarTheme {
//        TextDropDown(
//            hint = "hint",
//            selectedText = "",
//            isDropDownOpened = false,
//            options = listOf("1", "2"),
//            onOpenDropDown = { /*TODO*/ },
//            onSelectOption = {
//
//            },
//            onDismiss = {}
//        )
//    }
//}