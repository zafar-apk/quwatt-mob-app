package ui.trips.filter.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.domain.util.getCurrentDateTime
import core.domain.util.stringResource
import core.domain.util.toYYYYmmDD
import kotlinx.datetime.*
import ui.theme.Gray
import tj.yakroh.yakrohapp.SharedRes

@Composable
fun FilterDatePicker(
    modifier: Modifier = Modifier,
    hint: String,
    selectedText: String,
    onShowDatePicker: () -> Unit,
    onDateSelected: (String) -> Unit
) {
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
                Row(
                    modifier = Modifier.clickable { onShowDatePicker() }) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = selectedText,
                        style = MaterialTheme.typography.subtitle1,
                        color = MaterialTheme.colors.onBackground
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextWithBackground(
                    text = stringResource(SharedRes.strings.today),
                    onClick = {
                        val current = getCurrentDateTime()
                        onDateSelected(current.date.toYYYYmmDD())
                    },
                    textColor = Color(0xff0171CE)
                )
                TextWithBackground(
                    text = stringResource(SharedRes.strings.tomorrow),
                    onClick = {
                        val current = getCurrentDateTime()
                        val tomorrow = current.date.plus(1, DateTimeUnit.DAY)
                        onDateSelected(tomorrow.toYYYYmmDD())
                    },
                    textColor = Color(0xff0171CE)
                )
                TextWithBackground(
                    text = stringResource(SharedRes.strings.specify_date),
                    onClick = { onShowDatePicker() },
                    textColor = Color.Black
                )
            }
        }
    }
}

@Composable
fun TextWithBackground(
    text: String,
    textColor: Color = Color.Unspecified,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .clickable { onClick() }
            .clip(RoundedCornerShape(4.dp))
            .background(Color(0xffF2F2F2))
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            text = text,
            color = textColor,
            style = MaterialTheme.typography.subtitle1
        )
    }
}