package ui.trips.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ActionItem(
    modifier: Modifier = Modifier,
    actionName: String,
    actionIcon: Painter,
    actionIconContentDescription: String,
    onAction: () -> Unit
) {
    Card(
        modifier = modifier,
        onClick = { onAction() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = actionName,
                style = MaterialTheme.typography.subtitle1
            )

            Icon(
                modifier = Modifier.size(24.dp),
                painter = actionIcon,
                contentDescription = actionIconContentDescription
            )
        }
    }
}