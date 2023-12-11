package ui.trips.booking.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import trips.booking.domain.BookTrip
import trips.booking.presentation.BookingTripScreenEvent
import trips.booking.presentation.BookingTripScreenState
import trips.booking.presentation.BookingTripViewModel

class BookingTripScreenAndroidViewModel (
    private val bookTrip: BookTrip
) : ViewModel() {
    private val viewModel by lazy {
        BookingTripViewModel(
            coroutineScope = viewModelScope,
            bookTrip = bookTrip
        )
    }
    val state: CommonStateFlow<BookingTripScreenState> = viewModel.state

    fun onEvent(event: BookingTripScreenEvent) = viewModel.onEvent(event)
}