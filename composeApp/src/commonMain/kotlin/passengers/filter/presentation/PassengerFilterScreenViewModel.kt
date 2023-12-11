package passengers.filter.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.cities.use_case.GetCities
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import passengers.filter.domain.models.AvailablePassengerTrips
import passengers.filter.domain.usecases.GetAvailablePassengerTrips
import passengers.filter.presentation.mapper.copyFilterFields
import passengers.filter.presentation.mapper.toPassengerTripsFilter

class PassengerFilterScreenViewModel(
    private val getCities: GetCities,
    private val getAvailablePassengerTrips: GetAvailablePassengerTrips,
    private val coroutineScope: CoroutineScope?,
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private var availablePassengerTripsLoadingJob: Job? = null

    private val _state = MutableStateFlow(PassengerFilterScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PassengerFilterScreenState()
    ).toCommonStateFlow()

    init {
        getCities()
        loadAvailableTrips()
    }

    fun onEvent(event: PassengerFilterScreenEvent) {
        when (event) {
            PassengerFilterScreenEvent.ResetFilter -> _state.update {
                PassengerFilterScreenState(cities = it.cities, resetPriceValues = true)
            }.also { loadAvailableTrips() }


            PassengerFilterScreenEvent.OnPriceValuesReseted -> _state.update {
                it.copy(resetPriceValues = false)
            }

            PassengerFilterScreenEvent.OpenToCityDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingToCity = true)
                screenState.copy(choosingState = choosingState)
            }

            PassengerFilterScreenEvent.OpenFromCityDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingFromCity = true)
                screenState.copy(choosingState = choosingState)
            }
            PassengerFilterScreenEvent.OpenTripDateCalendar -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingDate = true)
                screenState.copy(choosingState = choosingState)
            }
            PassengerFilterScreenEvent.OpenTripTimeDialog -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingTimeDate = true)
                screenState.copy(choosingState = choosingState)
            }

            PassengerFilterScreenEvent.OpenTripRatingDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingRating = true)
                screenState.copy(choosingState = choosingState)
            }

            PassengerFilterScreenEvent.DismissAllChoosing -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(
                    isChoosingFromCity = false,
                    isChoosingDate = false,
                    isChoosingTimeDate = false,
                    isChoosingRating = false,
                    isChoosingToCity = false,
                )
                screenState.copy(choosingState = choosingState)
            }

            is PassengerFilterScreenEvent.ChangePriceFrom -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedFromPriceTrip = event.price)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangePriceTo -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedToPriceTrip = event.price)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangeFromCity -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedFromCity = event.city)
                val choosingState = screenState.choosingState.copy(isChoosingFromCity = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangeToCity -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedToCity = event.city)
                val choosingState = screenState.choosingState.copy(isChoosingToCity = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangeTripDate -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedTripDate = event.date)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangeTripTime -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedTripTime = event.time)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.ChangeTripRating -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedTripRating = event.rating)
                val choosingState = screenState.choosingState.copy(isChoosingRating = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.PassengersFilterDataReturned -> _state.update {
                it.copyFilterFields(event.filter)
            }.also { loadAvailableTrips() }

            is PassengerFilterScreenEvent.Submit -> _state.update {
                it.copy(shouldGoBackWithResult = true)
            }

            else -> Unit
        }
    }

    private fun getCities() = viewModelScope.launch {
        val result = getCities.execute()
        when (result) {
            is Resource.Success -> _state.update { it.copy(cities = result.data.orEmpty()) }
            is Resource.Error -> _state.update { it.copy(error = result.throwable?.message.toString()) }
        }
    }


    private fun loadAvailableTrips() {
        availablePassengerTripsLoadingJob?.cancel()
        availablePassengerTripsLoadingJob = viewModelScope.launch {
            val filterState = state.value
            val result = getAvailablePassengerTrips(filterState.toPassengerTripsFilter())
            processResult(result)
        }
    }

    private fun processResult(result: Resource<AvailablePassengerTrips>) = when (result) {
        is Resource.Success -> _state.update {
            it.copy(availableTrips = result.data?.count ?: 0)
        }
        is Resource.Error -> _state.update {
            it.copy(error = result.throwable?.message.toString(), isLoading = false)
        }
    }
}