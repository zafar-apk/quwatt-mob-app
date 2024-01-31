package ui.stations.all.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.icerock.moko.resources.compose.stringResource
import kotlinx.coroutines.launch
import stations.all.domain.models.Station
import stations.all.presentation.StationsScreenEvent
import stations.all.presentation.StationsScreenState
import tj.ham_safar.app.android.core.presentation.components.ActionButton
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
                        item {
                            if (state.stations.isNotEmpty()) {
                                Text(
                                    modifier = Modifier.padding(top = 8.dp),
                                    text = stringResource(SharedRes.strings.all_trips),
                                    style = MaterialTheme.typography.subtitle1,
                                    fontSize = 18.sp
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        items(state.stations, key = Station::id) { station ->
                            ChargingStationItem(
                                station = station,
                                onClick = { onEvent(StationsScreenEvent.RequestToOpenChargingStation(station.id)) }
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

@Composable
fun StationsTopBar(
    onOpenFilters: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp)
            .padding(top = 24.dp)
            .background(color = MaterialTheme.colors.background),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(modifier = Modifier.size(32.dp))

        Text(
            text = stringResource(SharedRes.strings.charging_stations),
            style = MaterialTheme.typography.body1.copy(fontSize = 20.sp),
            textAlign = TextAlign.Center
        )
        ActionButton(
            icon = Icons.Default.Tune,
            onClick = { onOpenFilters() },
            contentDescription = stringResource(SharedRes.strings.filters)
        )
    }
}

//@Preview
//@Composable
//private fun AllTripsScreenPreview() {
//    HamSafarTheme {
//        AllTripsScreen(
//            TripsScreenState(),
//            onEvent = {}
//        )
//    }
//}