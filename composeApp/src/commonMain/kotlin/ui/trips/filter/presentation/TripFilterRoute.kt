package ui.trips.filter.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import trips.filter.presentation.TripFilterScreenEvent
import trips.filter.presentation.TripFilterScreenViewModel
import ui.trips.core.models.TripsFilter

private const val tripFilterRoute = "trips-filter"

fun RouteBuilder.addToGraphTripFilterScreen(
    onGoBack: () -> Unit,
    onGoBackWithResult: (filter: TripsFilter?) -> Unit
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
                        onGoBackWithResult(event.filterResult as? TripsFilter)

                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

suspend fun Navigator.navigateToTripFilterScreen(): TripsFilter? = navigateForResult(
    route = tripFilterRoute
) as? TripsFilter

