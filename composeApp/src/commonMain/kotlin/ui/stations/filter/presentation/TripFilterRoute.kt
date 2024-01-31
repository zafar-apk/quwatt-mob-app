package ui.stations.filter.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import stations.filter.presentation.TripFilterScreenEvent
import stations.filter.presentation.TripFilterScreenViewModel
import ui.stations.core.models.StationsFilter

private const val tripFilterRoute = "trips-filter"

fun RouteBuilder.addToGraphTripFilterScreen(
    onGoBack: () -> Unit,
    onGoBackWithResult: (filter: StationsFilter?) -> Unit
) {
    scene(route = tripFilterRoute) {
        val viewModel = koinViewModel(TripFilterScreenViewModel::class)
        val state by viewModel.state.collectAsState()
        TripFilterScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    is TripFilterScreenEvent.BackPress -> onGoBack()
                    is TripFilterScreenEvent.GoBackWithResult<*> ->
                        onGoBackWithResult(event.filterResult as? StationsFilter)

                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

suspend fun Navigator.navigateToTripFilterScreen(): StationsFilter? = navigateForResult(
    route = tripFilterRoute
) as? StationsFilter

