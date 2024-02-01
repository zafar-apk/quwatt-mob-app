@file:OptIn(ExperimentalFoundationApi::class)

package ui.stations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes
import ui.stations.all.presentation.getDisplayAddress
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
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StationImageBox(
                station = station,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )

            TextWithIcon(
                text = station.getDisplayAddress(),
                textStyle = MaterialTheme.typography.subtitle1,
                painter = painterResource(SharedRes.images.ic_location),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            TextWithIcon(
                text = getStationPrices(station),
                textStyle = MaterialTheme.typography.subtitle2,
                painter = painterResource(SharedRes.images.ic_money),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            )

            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
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
            modifier = Modifier.size(24.dp)
        )

        Spacer(Modifier.size(4.dp))

        Text(
            text = text,
            style = textStyle
        )
    }
}