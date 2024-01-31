package tj.ham_safar.app.trips.detailed_trip.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import tj.ham_safar.app.trips.detailed_trip.domain.use_case.GetTripById
import stations.detailed_trip.presentation.DetailedTripScreenState

class DetailedTripScreenViewModel(
    private val coroutineScope: CoroutineScope?,
    private val getTripById: GetTripById,
) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(DetailedTripScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        DetailedTripScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: DetailedTripScreenEvent) {
        when (event) {
            is DetailedTripScreenEvent.LoadTrip -> getTripById(event.tripId)

            is DetailedTripScreenEvent.RequestToBookingNavigation -> _state.update {
                it.copy(shouldOpenBookingTrip = true)
            }
            is DetailedTripScreenEvent.ResetNavigation -> _state.update {
                it.copy(shouldOpenBookingTrip = false)
            }
            else -> {}
        }
    }

    private fun getTripById(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = getTripById.execute(id)) {

            is Resource.Success -> _state.update {
                it.copy(station = result.data!!, isLoading = false)
            }

            is Resource.Error -> _state.update {
                it.copy(error = result.throwable?.message.toString(), isLoading = false)
            }
        }
    }
}