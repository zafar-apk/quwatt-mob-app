package ui.stations.details.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.presentation.LocalPaddings
import dev.icerock.moko.resources.compose.stringResource
import stations.detailed_trip.presentation.StationDetailsAction
import stations.detailed_trip.presentation.StationDetailsEvent
import stations.detailed_trip.presentation.StationDetailsState
import tj.quwatt.quwattapp.SharedRes
import ui.core.presentation.components.Loader
import ui.core.presentation.components.TopBar
import ui.stations.all.presentation.getDisplayAddress
import ui.stations.all.presentation.getFavoriteButtonPainter
import ui.stations.components.StationImageBox
import ui.stations.details.presentation.components.ConnectorItem

@Composable
fun StationDetailsScreen(
    state: StationDetailsState,
    onEvent: (StationDetailsEvent) -> Unit,
    onAction: (StationDetailsAction) -> Unit
) {
    SideEffect {
        println("StationDetailsScreen: $state")
    }
    val paddings = LocalPaddings.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopBar(
                text = state.station?.getDisplayAddress()
                    ?: stringResource(SharedRes.strings.stations),
                onBackButtonClick = {
                    onAction(StationDetailsAction.OnBackClick)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                state.station?.isFavorite?.let { isFavorite ->
                    Icon(
                        painter = getFavoriteButtonPainter(isFavorite),
                        contentDescription = stringResource(SharedRes.strings.favorites),
                    )
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(paddings.medium)
        ) {
            item {
                StationImageBox(
                    station = state.station,
                    modifier = Modifier.fillMaxWidth(),
                    imageHeight = 200.dp
                )
            }

            item {
                Text(
                    text = stringResource(SharedRes.strings.connectors),
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.padding(vertical = paddings.medium)
                )
            }

            items(state.connectors) { connector ->
                ConnectorItem(
                    connector = connector,
                    modifier = Modifier.fillMaxWidth()
                        .padding(bottom = paddings.medium)
                )
            }
        }
    }

    if (state.isLoading) {
        Loader()
    }
}