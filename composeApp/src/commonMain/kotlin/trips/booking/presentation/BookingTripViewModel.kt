package trips.booking.presentation

import io.ktor.http.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import trips.booking.domain.BookTrip
import trips.booking.presentation.BookingTripScreenEvent
import trips.booking.presentation.BookingTripScreenState
import trips.booking.presentation.TripData

class BookingTripViewModel(
    private val bookTrip: BookTrip,
    private val coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(BookingTripScreenState())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BookingTripScreenState()
    ).toCommonStateFlow()


    fun onEvent(event: BookingTripScreenEvent) {
        when (event) {
            BookingTripScreenEvent.OnCountMinus -> {
                if (state.value.count > 1) _state.update { screenState ->
                    screenState
                        .copy(count = screenState.count - 1)
                        .updateCounterAvailability()
                }
            }

            BookingTripScreenEvent.OnCountPlus -> {
                val currentState = state.value
                if (currentState.isThereAvailableSeat()) _state.update { screenState ->
                    screenState
                        .copy(count = screenState.count + 1)
                        .updateCounterAvailability()
                }
            }

            BookingTripScreenEvent.RequestBookingConfirmation -> _state.update {
                it.copy(shouldRequestBookingConfirmation = true)
            }

            BookingTripScreenEvent.ResetState -> _state.update {
                it.copy(shouldRequestBookingConfirmation = false, shouldGoBack = false)
            }

            BookingTripScreenEvent.BookATrip -> {
                _state.update { it.copy(shouldRequestBookingConfirmation = false) }
                val tripData = state.value.tripData
                if (tripData != null && tripData.hasAvailableSeats) bookATrip(tripData)
            }

            is BookingTripScreenEvent.OnTripData -> _state.update { screenState ->
                screenState
                    .copy(tripData = TripData(event.tripId, event.availableSeatsIds))
                    .updateCounterAvailability()
            }

            else -> Unit
        }
    }

    private fun BookingTripScreenState.updateCounterAvailability() = copy(
        isPositiveCounterAvailable = isThereAvailableSeat(),
        isNegativeCounterAvailable = count > 1
    )


    private fun BookingTripScreenState.isThereAvailableSeat(): Boolean {
        val tripData = tripData ?: return false
        return tripData.availableSeatsIds.size > count
    }

    private fun bookATrip(tripData: TripData) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        val seatsIds = List(state.value.count) { index ->
            tripData.availableSeatsIds[index]
        }
        val result = bookTrip(tripData.tripId, seatsIds)
        processResult(result)
    }


    private fun processResult(result: Resource<Unit>) = when (result) {
        is Resource.Success -> _state.update {
            it.copy(isLoading = false, shouldGoBack = true)
        }
        is Resource.Error -> _state.update {
            if (result withCode HttpStatusCode.Conflict)
                it.copy(error = result.throwable?.message, isLoading = false, shouldGoBack = true)
            else
                it.copy(error = result.throwable?.message, isLoading = false)

        }
    }
}