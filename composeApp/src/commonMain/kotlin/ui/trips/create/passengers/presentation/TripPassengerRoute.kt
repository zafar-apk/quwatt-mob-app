package ui.trips.create.passengers.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import tj.ham_safar.app.trips.create.passengers.presentation.TripPassengersScreenEvent
import ui.core.presentation.StringRouteBuilder
import ui.core.presentation.changePlaceHoldersToArgs

private const val completeActionArg = "complete-action"

private val tripPassengerRoute: String =
    StringRouteBuilder("trip-create-passengers")
        .addArgPlaceHolder(completeActionArg)
        .build()

fun RouteBuilder.addToGraphTripPassengerScreen(
    onGoBack: (route: String?) -> Unit,
    onSuccessfullyRequestLeft: () -> Unit
) {
    scene(route = tripPassengerRoute) {
        val completeActionArg = it.path<Int>(completeActionArg)!!
        val viewModel: TripPassengerAndroidViewModel = koinViewModel(TripPassengerAndroidViewModel::class)
        val state by viewModel.state.collectAsState()
        TripPassengerScreen(state = state) { event ->
            when (event) {
                is TripPassengersScreenEvent.GoBack -> onGoBack(event.route)
                is TripPassengersScreenEvent.GoNext -> onSuccessfullyRequestLeft()
                else -> viewModel.onEvent(event)
            }
        }
    }
}

fun Navigator.navigateToTripPassengerScreen(completeAction: Int) = navigate(
    route = tripPassengerRoute.changePlaceHoldersToArgs(
        completeActionArg to completeAction.toString()
    )
)
