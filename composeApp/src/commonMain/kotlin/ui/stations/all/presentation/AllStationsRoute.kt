package ui.stations.all.presentation

import androidx.compose.runtime.getValue
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.RouteBuilder
import stations.all.presentation.ChargingStationsScreenViewModel
import stations.all.presentation.StationsScreenEvent
import stations.all.presentation.StationsScreenState
import ui.stations.core.models.StationsFilter

const val allStationsRoute = "stations"

fun RouteBuilder.addAllStationsScreenToGraph(
    onNavigateToStationsFilterForResult: suspend () -> StationsFilter?,
    onNavigateToStationDetails: (tripItemId: Int) -> Unit,
    onNavigateToLogin: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    scene(route = allStationsRoute) {
        val viewModel = koinViewModel(ChargingStationsScreenViewModel::class)
        val state by viewModel.models.collectAsStateWithLifecycle(StationsScreenState())
        AllStationsScreen(
            state = state,
            openStationsFilterForResult = onNavigateToStationsFilterForResult,
            onEvent = { event ->
                when (event) {
                    is StationsScreenEvent.NavigateToChargingStation -> onNavigateToStationDetails(event.id)
                    is StationsScreenEvent.NavigateToLoginScreen -> onNavigateToLogin()
                    is StationsScreenEvent.NavigateToProfileScreen -> onNavigateToProfile()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}
