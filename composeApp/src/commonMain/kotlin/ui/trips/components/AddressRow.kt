package ui.trips.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.stringResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.core.presentation.painterResource
import ui.theme.TextBlack

@OptIn(ExperimentalResourceApi::class)
@Composable
fun AddressRow(
    modifier: Modifier = Modifier,
    textColor: Color = TextBlack,
    iconColor: Color,
    text: String,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource("ic_location.xml"),
            contentDescription = stringResource(id = "location_icon"),
            tint = iconColor
        )
        Spacer(modifier = Modifier.size(6.dp))
        Text(text = text, style = MaterialTheme.typography.body1, color = textColor)
    }
}