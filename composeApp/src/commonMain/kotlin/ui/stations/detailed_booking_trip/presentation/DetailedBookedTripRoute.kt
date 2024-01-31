package ui.stations.detailed_booking_trip.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import moe.tlaster.precompose.navigation.path
import ui.core.presentation.StringRouteBuilder
import ui.core.presentation.changePlaceHoldersToArgs
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenEvent


private const val bookedTripIdArg = "booked-trip-id-arg"
private val detailedBookedTripRoute = StringRouteBuilder("detailed_booked_trip")
    .addArgPlaceHolder(bookedTripIdArg)
    .build()


fun RouteBuilder.addToGraphDetailedBookedTripScreen(
    onGoBack: () -> Unit
) {
    scene(route = detailedBookedTripRoute) {
        val viewModel = koinViewModel(DetailedBookedTripScreenAndroidViewModel::class)
        LaunchedEffect(false) {
            it.path<Int>(bookedTripIdArg)?.let { id ->
                viewModel.onEvent(DetailedBookedTripScreenEvent.LoadTrip(id))
            }
        }
        val state by viewModel.state.collectAsState()
        DetailedBookedTripScreen(
            state = state,
            onEvent = { event ->
                when (event) {
                    is DetailedBookedTripScreenEvent.OnBackClick -> onGoBack()
                    is DetailedBookedTripScreenEvent.GoBack -> onGoBack()
                    is DetailedBookedTripScreenEvent.WriteMessage -> {}
                    else -> viewModel.onEvent(event)
                }
            })
    }
}

fun Navigator.navigateToDetailedBookedTripScreen(bookedTripId: Int) = navigate(
    route = detailedBookedTripRoute.changePlaceHoldersToArgs(
        bookedTripIdArg to bookedTripId.toString()
    )
)
