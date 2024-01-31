package stations.filter.presentation

import core.domain.cities.use_case.GetCities
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.filter.domain.models.AvailableTrips
import tj.ham_safar.app.trips.filter.domain.usecases.GetAvailableTrips
import tj.ham_safar.app.trips.filter.presentation.mapper.toTripsFilter

class TripFilterScreenViewModel(
    private val getCities: GetCities,
    private val getAvailableTrips: GetAvailableTrips
) : ViewModel() {

    private var availableTripsLoadingJob: Job? = null

    private val _state = MutableStateFlow(TripFilterScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        TripFilterScreenState()
    ).toCommonStateFlow()

    init {
        getCities()
        loadAvailableTrips()
    }

    fun onEvent(event: TripFilterScreenEvent) {
        when (event) {
            TripFilterScreenEvent.ResetFilter -> _state.update {
                TripFilterScreenState(cities = it.cities, resetPriceValues = true)
            }.also { loadAvailableTrips() }

            TripFilterScreenEvent.OnPriceValuesReseted -> _state.update {
                it.copy(resetPriceValues = false)
            }

            TripFilterScreenEvent.OpenAutoTypeDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingAutoType = true)
                screenState.copy(choosingState = choosingState)

            }

            TripFilterScreenEvent.OpenAutoModelDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingAutoModel = true)
                screenState.copy(choosingState = choosingState)
            }

            TripFilterScreenEvent.OpenToCityDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingToCity = true)
                screenState.copy(choosingState = choosingState)

            }

            TripFilterScreenEvent.OpenFromCityDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingFromCity = true)
                screenState.copy(choosingState = choosingState)
            }

            TripFilterScreenEvent.OpenTripDateCalendar -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingDate = true)
                screenState.copy(choosingState = choosingState)
            }

            TripFilterScreenEvent.OpenTripTimeDialog -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingTimeDate = true)
                screenState.copy(choosingState = choosingState)
            }

            TripFilterScreenEvent.OpenTripRatingDropDown -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(isChoosingRating = true)
                screenState.copy(choosingState = choosingState)
            }

            TripFilterScreenEvent.DismissAllChoosing -> _state.update { screenState ->
                val choosingState = screenState.choosingState.copy(
                    isChoosingAutoModel = false,
                    isChoosingAutoType = false,
                    isChoosingFromCity = false,
                    isChoosingDate = false,
                    isChoosingTimeDate = false,
                    isChoosingRating = false,
                    isChoosingToCity = false,
                )
                screenState.copy(choosingState = choosingState)
            }

            is TripFilterScreenEvent.ChangeAutoType -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedAutoType = event.autoType)
                val choosingState = screenState.choosingState.copy(isChoosingAutoType = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeAutoModel -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedAutoModel = event.autoModel)
                val choosingState = screenState.choosingState.copy(isChoosingAutoModel = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangePriceFrom -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedFromPriceTrip = event.price)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangePriceTo -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedToPriceTrip = event.price)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeFromCity -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedFromCity = event.city)
                val choosingState = screenState.choosingState.copy(isChoosingFromCity = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeToCity -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedToCity = event.city)
                val choosingState = screenState.choosingState.copy(isChoosingToCity = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeTripDate -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedTripDate = event.date)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeTripTime -> _state.update { screenState ->
                val selectedState = screenState.selectedState.copy(selectedTripTime = event.time)
                screenState.copy(selectedState = selectedState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.ChangeTripRating -> _state.update { screenState ->
                val selectedState =
                    screenState.selectedState.copy(selectedTripRating = event.rating)
                val choosingState = screenState.choosingState.copy(isChoosingRating = false)
                screenState.copy(selectedState = selectedState, choosingState = choosingState)
            }.also { loadAvailableTrips() }

            is TripFilterScreenEvent.Submit -> _state.update {
                it.copy(shouldGoBackWithResult = true)
            }

            else -> Unit
        }
    }

    private fun getCities() = viewModelScope.launch {
        when (val result = getCities.execute()) {
            is Resource.Success -> _state.update { it.copy(cities = result.data.orEmpty()) }
            is Resource.Error -> _state.update { it.copy(error = result.throwable?.message.toString()) }
        }
    }

    private fun loadAvailableTrips() {
        availableTripsLoadingJob?.cancel()
        availableTripsLoadingJob = viewModelScope.launch {
            val filterState = state.value
            val query = filterState.toTripsFilter()
            val result = getAvailableTrips(query)
            processResult(result)
        }
    }

    private fun processResult(result: Resource<AvailableTrips>) = when (result) {
        is Resource.Success -> _state.update {
            it.copy(availableTrips = result.data?.count ?: 0)
        }

        is Resource.Error -> _state.update {
            it.copy(error = result.throwable?.message.toString(), isLoading = false)
        }
    }
}