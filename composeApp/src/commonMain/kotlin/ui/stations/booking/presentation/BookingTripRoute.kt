package ui.stations.booking.presentation

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import moe.tlaster.precompose.koin.koinViewModel
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.RouteBuilder
import stations.booking.presentation.BookingTripScreenEvent

const val tripDataKey = "trip-data-key"
private const val bookingTripRoute: String = "booking-trip"

fun RouteBuilder.addToGraphBookingTripScreen(
    onGoBack: () -> Unit,
//    onTripDataResult: LiveData<TripDataParcelize>?
) {
    scene(
        route = bookingTripRoute,
    ) {
        val viewModel: BookingTripScreenAndroidViewModel = koinViewModel(
            BookingTripScreenAndroidViewModel::class)
//        val tripDataParcelize = onTripDataResult?.observeAsState()

//        LaunchedEffect(tripDataParcelize) {
//            val tripData = tripDataParcelize?.value
//            if (tripData != null) viewModel.onEvent(
//                BookingTripScreenEvent.OnTripData(tripData.tripId, tripData.availableSeatsIds)
//            )
//        }
        val state by viewModel.state.collectAsState()
        BookingTripScreen(state) {
            when (it) {
                is BookingTripScreenEvent.GoBack -> onGoBack()
                else -> viewModel.onEvent(it)
            }
        }
    }

}

fun Navigator.navigateToBookingScreen() = navigate(
    route = bookingTripRoute
)
