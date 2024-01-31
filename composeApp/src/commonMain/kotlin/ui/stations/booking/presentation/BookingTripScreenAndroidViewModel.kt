package ui.stations.booking.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stations.booking.domain.BookTrip
import stations.booking.presentation.BookingTripScreenEvent
import stations.booking.presentation.BookingTripScreenState
import stations.booking.presentation.BookingTripViewModel

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