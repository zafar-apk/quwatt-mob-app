package ui.passengers.filter.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import passengers.filter.presentation.PassengerFilterScreenEvent
import ui.passengers.core.shared_trip_filter.PassengerTripsFilterParcelize

private const val passengerFilterRoute = "passengers-filter"

fun RouteBuilder.addToGraphPassengerFilterScreen(
    onGoBack: () -> Unit,
    onGoBackWithResult: (filter: PassengerTripsFilterParcelize) -> Unit,
//    onFilterResultReturned: LiveData<PassengerTripsFilterParcelize>?
) {
    scene(route = passengerFilterRoute) {
        val viewModel = koinViewModel(PassengerFilterScreenAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
//        val filterResultState = onFilterResultReturned?.observeAsState()

        PassengerFilterScreen(
//            filterResultState,
            returnedFilterState = null,
            state = state,
            onEvent = { event ->
                when (event) {
                    is PassengerFilterScreenEvent.BackPress -> onGoBack()
                    is PassengerFilterScreenEvent.GoBackWithResult<*> ->
                        onGoBackWithResult(event.filterResult as PassengerTripsFilterParcelize)
                    else -> viewModel.onEvent(event)
                }
            }
        )
    }
}

fun Navigator.navigateToPassengerFilterScreen() = navigate(
    route = passengerFilterRoute
)

