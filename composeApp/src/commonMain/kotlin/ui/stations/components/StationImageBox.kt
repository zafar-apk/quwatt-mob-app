@file:OptIn(ExperimentalFoundationApi::class)

package ui.stations.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import stations.all.domain.models.Station
import tj.quwatt.quwattapp.SharedRes
import ui.stations.all.presentation.getStationPainter

@Composable
fun StationImageBox(
    station: Station?,
    imageHeight: Dp = 180.dp,
    modifier: Modifier = Modifier,
    overlay: @Composable BoxScope.() -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val imagesScrollState = rememberLazyListState()
    Box(modifier = modifier) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            state = imagesScrollState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState = imagesScrollState)
        ) {
            items(station?.images.orEmpty()) { image ->
                Image(
                    painter = getStationPainter(image),
                    contentDescription = stringResource(SharedRes.strings.charging_stations),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillParentMaxWidth().height(imageHeight)
                        .clip(MaterialTheme.shapes.extraLarge)
                        .padding(horizontal = 8.dp)
                )
            }
        }

        if ((station?.images?.size ?: 0) > 1) {
            LazyRow(
                modifier = Modifier.align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp)
            ) {
                items(station?.images?.size ?: 0) { position ->
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

        overlay()
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
                color = if (isSelected) MaterialTheme.colorScheme.primary
                else Color.White,
                shape = CircleShape
            )
            .border(
                width = 1.dp,
                color = if (isSelected) Color.White
                else MaterialTheme.colorScheme.primary,
                shape = CircleShape
            )
            .clickable(onClick = onClick)
    )
}
