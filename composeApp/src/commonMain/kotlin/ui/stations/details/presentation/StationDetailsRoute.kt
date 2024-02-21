package ui.stations.details.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import stations.detailed_trip.presentation.StationDetailsAction
import stations.detailed_trip.presentation.StationDetailsEvent
import stations.detailed_trip.presentation.StationDetailsViewModel

private const val argPath = "{stationId}"
private const val route = "station_details/{stationId}"
private const val stationId = "stationId"

fun RouteBuilder.addStationDetailsToGraph(navController: Navigator) {
    scene(route = route) { backStackEntry ->
        val viewModel = koinViewModel(StationDetailsViewModel::class)
        LaunchedEffect(false) {
            backStackEntry.path<Int>(stationId)?.let {
                viewModel.onEvent(StationDetailsEvent.LoadStationDetails(it))
            }
        }
        val state by viewModel.state.collectAsState()
        StationDetailsScreen(
            state = state,
            onEvent = viewModel::onEvent,
            onAction = { action ->
                when (action) {
                    StationDetailsAction.OnBackClick -> navController.popBackStack()
                }
            }
        )
    }
}

fun Navigator.navigateToStationDetails(stationId: Int) {
    navigate(route.replace(argPath, stationId.toString()))
}