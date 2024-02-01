@file:OptIn(ExperimentalFoundationApi::class)

package ui.stations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes
import ui.stations.all.presentation.getFavoriteButtonPainter
import ui.stations.all.presentation.getStationPainter

@Composable
fun StationImageBox(
    station: Station,
    modifier: Modifier = Modifier
) {
    val coroutineScope = rememberCoroutineScope()
    val imagesScrollState = rememberLazyListState()
    Box(modifier = modifier) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = imagesScrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = imagesScrollState)
        ) {
            items(station.images) { image ->
                Image(
                    painter = getStationPainter(image),
                    contentDescription = stringResource(SharedRes.strings.charging_stations),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillParentMaxWidth().height(180.dp)
                        .padding(horizontal = 8.dp)
                )
            }
        }

        if (station.images.size > 1) {
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
                    Spacer(Modifier.size(8.dp))
                }
            }
        }

        Text(
            text = station.status,
            style = MaterialTheme.typography.subtitle2.copy(color = Color.White),
            modifier = Modifier.align(Alignment.TopStart)
                .padding(top = 8.dp, start = 16.dp)
                .background(
                    color = if (station.status == "Available") Color.Green
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
}

@Composable
fun Dot(
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(12.dp)
            .background(
                color = if (isSelected) MaterialTheme.colors.primary
                else Color.White,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.White
                else MaterialTheme.colors.primary,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}
