package ui.stations.detailed_booking_trip.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.CancelBookedTrip
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.GetBookedTripById
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenEvent
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenState
import tj.ham_safar.app.trips.detailed_booking_trip.presentation.DetailedBookedTripScreenViewModel

class DetailedBookedTripScreenAndroidViewModel (
    getBookedTripById: GetBookedTripById,
    cancelBookedTrip: CancelBookedTrip
) : ViewModel() {
    private val viewModel by lazy {
        DetailedBookedTripScreenViewModel(
            coroutineScope = viewModelScope,
            getBookedTripById = getBookedTripById,
            cancelBookedTrip = cancelBookedTrip
        )
    }
    val state: CommonStateFlow<DetailedBookedTripScreenState> = viewModel.state

    fun onEvent(event: DetailedBookedTripScreenEvent) = viewModel.onEvent(event)
}