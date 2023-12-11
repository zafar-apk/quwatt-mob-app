package tj.ham_safar.app.trips.detailed_booking_trip.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.CancelBookedTrip
import tj.ham_safar.app.trips.detailed_booking_trip.domain.use_case.GetBookedTripById

class DetailedBookedTripScreenViewModel(
    private val coroutineScope: CoroutineScope?,
    private val cancelBookedTrip: CancelBookedTrip,
    private val getBookedTripById: GetBookedTripById,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(DetailedBookedTripScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailedBookedTripScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: DetailedBookedTripScreenEvent) {
        when (event) {
            is DetailedBookedTripScreenEvent.LoadTrip -> getBookedTripById(event.tripId)

            is DetailedBookedTripScreenEvent.ResetState -> _state.update {
                it.copy(
                    shouldRequestBookingCancellationConfirmation = false,
                    toastError = null
                )
            }

            is DetailedBookedTripScreenEvent.RequestBookedTripCancellationConfirmation ->
                _state.update { it.copy(shouldRequestBookingCancellationConfirmation = true) }

            is DetailedBookedTripScreenEvent.CancelBookedTrip -> {
                cancelBookedTrip()
                _state.update { it.copy(shouldRequestBookingCancellationConfirmation = false) }
            }

            else -> {}
        }
    }

    private fun cancelBookedTrip() = viewModelScope.launch {
        val id = state.value.trip?.id ?: return@launch
        _state.update { it.copy(isLoading = true) }
        when (val result = cancelBookedTrip.execute(id)) {

            is Resource.Success -> _state.update {
                it.copy(shouldGoBack = true, isLoading = false)
            }

            is Resource.Error -> _state.update {
                it.copy(toastError = result.throwable?.message.toString(), isLoading = false)
            }
        }
    }

    private fun getBookedTripById(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = getBookedTripById.execute(id)) {

            is Resource.Success -> _state.update {
                it.copy(trip = result.data!!, isLoading = false)
            }

            is Resource.Error -> _state.update {
                it.copy(error = result.throwable?.message.toString(), isLoading = false)
            }
        }
    }
}