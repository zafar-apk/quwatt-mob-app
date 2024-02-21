package ui.stations.all.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveIconButton
import io.github.alexzhirkevich.cupertino.adaptive.AdaptiveTopAppBar
import io.github.alexzhirkevich.cupertino.adaptive.ExperimentalAdaptiveApi
import kotlinx.coroutines.launch
import stations.all.domain.models.Station
import stations.all.presentation.StationsScreenEvent
import stations.all.presentation.StationsScreenState
import tj.quwatt.quwattapp.SharedRes
import ui.core.presentation.components.ErrorView
import ui.core.presentation.dialogs.RequestAuthorizationDialog
import ui.stations.components.ChargingStationItem
import ui.stations.core.models.StationsFilter

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AllStationsScreen(
    state: StationsScreenState,
    openStationsFilterForResult: suspend () -> StationsFilter?,
    onEvent: (StationsScreenEvent) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onEvent(StationsScreenEvent.LoadStations) }
    )
    LaunchedEffect(state) {
        when {
            state.shouldRegisterUserInfo -> onRegisterUserInfo(onEvent)

            state.stationDetail != null -> {
                onEvent(StationsScreenEvent.NavigateToChargingStation(state.stationDetail))
                onEvent(StationsScreenEvent.ResetNavigation)
            }
        }
    }

    if (state.shouldRegisterUserInfo) {
        Snackbar(content = {
            Text(core.domain.Strings.notRegistered)
        })
    }

    if (state.shouldRequestAuthorization) {
        RequestAuthorizationDialog(
            onLoginClicked = { onEvent(StationsScreenEvent.NavigateToLoginScreen) },
            onDismissRequest = { onEvent(StationsScreenEvent.CancelShowingAuthDialog) }
        )
    }

    Scaffold(
        topBar = {
            StationsTopBar {
                coroutineScope.launch {
                    onEvent(StationsScreenEvent.StationsFilterData(openStationsFilterForResult()))
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 21.dp)
                    .padding(top = 24.dp)
            ) {
                Box(modifier = Modifier.weight(1f)) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .pullRefresh(pullRefreshState)
                    ) {
                        items(state.stations, key = Station::id) { station ->
                            ChargingStationItem(
                                station = station,
                                onClick = {
                                    onEvent(
                                        StationsScreenEvent.RequestToOpenChargingStation(
                                            station.id
                                        )
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        item {
                            state.error?.let { error ->
                                ErrorView(
                                    onRetry = { onEvent(StationsScreenEvent.LoadStations) },
                                    modifier = Modifier.fillParentMaxSize(),
                                    error = error
                                )
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isLoading,
                state = pullRefreshState
            )
        }
    }

}

private fun onRegisterUserInfo(
    onEvent: (StationsScreenEvent) -> Unit
) {
    onEvent(StationsScreenEvent.NavigateToProfileScreen)
    onEvent(StationsScreenEvent.ResetNavigation)
}

@OptIn(ExperimentalAdaptiveApi::class)
@Composable
fun StationsTopBar(
    onOpenFilters: () -> Unit
) {
    AdaptiveTopAppBar(
        title = {
            Text(
                text = stringResource(SharedRes.strings.charging_stations),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
        },
        actions = {
            AdaptiveIconButton(
                onClick = onOpenFilters,
                content = {
                    Icon(
                        imageVector = Icons.Default.Tune,
                        contentDescription = stringResource(SharedRes.strings.filters),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            )
        }
    )
}