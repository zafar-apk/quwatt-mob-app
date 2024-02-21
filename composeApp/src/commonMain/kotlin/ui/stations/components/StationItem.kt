@file:OptIn(ExperimentalFoundationApi::class)

package ui.stations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes
import ui.stations.all.presentation.getDisplayAddress
import ui.stations.all.presentation.getFavoriteButtonPainter
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
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StationImageBox(
                station = station,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(
                    text = if (station.isAvailable) stringResource(SharedRes.strings.available)
                    else stringResource(SharedRes.strings.busy),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    modifier = Modifier.align(Alignment.TopStart)
                        .padding(top = 8.dp, start = 16.dp)
                        .background(
                            color = if (station.isAvailable) Color.Green
                            else Color.Red,
                            shape = MaterialTheme.shapes.small
                        )
                        .padding(2.dp)
                )

                Icon(
                    painter = getFavoriteButtonPainter(station.isFavorite),
                    contentDescription = stringResource(SharedRes.strings.charging_stations),
                    modifier = Modifier.padding(top = 8.dp, end = 16.dp)
                        .align(Alignment.TopEnd)
                        .clickable {

                        }
                )
            }

            TextWithIcon(
                text = station.getDisplayAddress(),
                textStyle = MaterialTheme.typography.bodyMedium,
                painter = painterResource(SharedRes.images.ic_location),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            TextWithIcon(
                text = getStationPrices(station),
                textStyle = MaterialTheme.typography.bodyMedium,
                painter = painterResource(SharedRes.images.ic_money),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                TextWithIcon(
                    text = getStationPower(station),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    painter = painterResource(SharedRes.images.ic_light)
                )

                Spacer(Modifier.weight(1F))

                TextWithIcon(
                    text = station.chargersCount.toString(),
                    textStyle = MaterialTheme.typography.bodyMedium,
                    painter = painterResource(SharedRes.images.ic_charger)
                )
            }

            Spacer(Modifier.height(8.dp))
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
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(Modifier.size(4.dp))

        Text(
            text = text,
            style = textStyle,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}