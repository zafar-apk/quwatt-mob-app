package tj.ham_safar.app.trips.my_trips.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import tj.ham_safar.app.trips.my_trips.domain.GetAllMyTrips
import stations.my_trips.presentation.MyTripsScreenEvent

class MyTripsScreenViewModel(
    private val getAllMyTrips: GetAllMyTrips,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(MyTripsScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = MyTripsScreenState()
    ).toCommonStateFlow()

    init {
        getAllMyTrips()
    }

    fun onEvent(event: MyTripsScreenEvent) {
        when (event) {
            is MyTripsScreenEvent.RequestToOpenTripDetails ->
                _state.update { it.copy(openTrip = event.id) }
            MyTripsScreenEvent.ResetNavigation -> _state.update { it.copy(openTrip = null) }
            MyTripsScreenEvent.LoadMyTrips -> getAllMyTrips()
            else -> Unit
        }
    }

    private fun getAllMyTrips() = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true,
                errorMessage = null
            )
        }
        when (val result = getAllMyTrips.execute()) {
            is Resource.Error -> _state.update {
                it.copy(
                    errorMessage = result.throwable?.message,
                    isLoading = false
                )
            }
            is Resource.Success -> _state.update {
                it.copy(
                    stations = result.data.orEmpty(),
                    isLoading = false
                )
            }
        }
    }
}