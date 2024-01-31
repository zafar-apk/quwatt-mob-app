package ui.stations.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes
import ui.stations.all.presentation.getDisplayAddress
import ui.stations.all.presentation.getFavoriteButtonPainter
import ui.stations.all.presentation.getStationPainter
import ui.stations.all.presentation.getStationPower
import ui.stations.all.presentation.getStationPrices

@Composable
fun ChargingStationItem(
    station: Station,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth()
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StationImageBox(
                station = station,
                modifier = Modifier.fillMaxWidth()
            )

            TextWithIcon(
                text = station.getDisplayAddress(),
                textStyle = MaterialTheme.typography.subtitle1,
                painter = painterResource(SharedRes.images.ic_location),
                modifier = Modifier.fillMaxWidth()
            )

            TextWithIcon(
                text = getStationPrices(station),
                textStyle = MaterialTheme.typography.subtitle2,
                painter = painterResource(SharedRes.images.ic_money),
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth()) {
                TextWithIcon(
                    text = getStationPower(station),
                    textStyle = MaterialTheme.typography.subtitle2,
                    painter = painterResource(SharedRes.images.ic_light)
                )

                Spacer(Modifier.weight(1F))

                TextWithIcon(
                    text = station.chargersCount.toString(),
                    textStyle = MaterialTheme.typography.subtitle1,
                    painter = painterResource(SharedRes.images.ic_charger)
                )
            }
        }
    }
}

@Composable
private fun TextWithIcon(
    text: String,
    textStyle: TextStyle,
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        Icon(
            painter = painter,
            contentDescription = stringResource(SharedRes.strings.charging_stations),
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.size(4.dp))

        Text(
            text = text,
            style = textStyle
        )
    }
}

@Composable
private fun StationImageBox(
    station: Station,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val imagesScrollState = rememberLazyListState()
    Box(modifier = modifier.padding(8.dp)) {
        Text(
            text = station.status,
            style = MaterialTheme.typography.subtitle2.copy(color = Color.White),
            modifier = Modifier.align(Alignment.TopStart)
                .padding(2.dp)
                .background(
                    color = if (station.status == "Available") Color.Green
                    else Color.Red,
                    shape = MaterialTheme.shapes.small
                )
                .padding(8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = imagesScrollState
        ) {
            items(station.images) { image ->
                Image(
                    painter = getStationPainter(image),
                    contentDescription = stringResource(SharedRes.strings.charging_stations),
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier.align(Alignment.BottomCenter)
                .padding(bottom = 16.dp)
        ) {
            items(station.images.size) { position ->
                val isSelected = imagesScrollState.firstVisibleItemIndex == position
                Dot(
                    isSelected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            imagesScrollState.animateScrollToItem(position)
                        }
                    }
                )
            }
        }

        Icon(
            painter = getFavoriteButtonPainter(station.isFavorite),
            contentDescription = stringResource(SharedRes.strings.charging_stations),
            modifier = Modifier.padding(8.dp)
                .align(Alignment.TopEnd)
                .clickable {

                }
        )
    }
}

@Composable
fun Dot(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(8.dp)
            .padding(4.dp)
            .background(
                color = if (isSelected) MaterialTheme.colors.primary
                else MaterialTheme.colors.onSurface.copy(alpha = 0.12f),
                shape = MaterialTheme.shapes.small
            )
            .clickable(onClick = onClick)
    )
}
