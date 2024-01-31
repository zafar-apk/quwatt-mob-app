package ui.stations.my_trips.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import stations.my_trips.presentation.MyTripsScreenEvent

private const val myTripsRoute = "my-trips-route"

fun RouteBuilder.addToGraphMyTripsScreen(
    onNavigateToTripDetail: (id: Int) -> Unit,
    onGoBack: () -> Unit
) {

    scene(route = myTripsRoute) {
        val viewModel: AllMyTripsAndroidViewModel = koinViewModel(AllMyTripsAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        MyTripsScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    is MyTripsScreenEvent.NavigateToTripDetails ->
                        onNavigateToTripDetail(event.id)
                    is MyTripsScreenEvent.GoBack -> onGoBack()
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

fun Navigator.navigateToMyTripsScreen() =
    navigate(route = myTripsRoute)
