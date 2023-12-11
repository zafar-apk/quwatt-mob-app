package tj.ham_safar.app.trips.create.location.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.Strings
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripLocation
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripLocationUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripLocationUseCase
import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import tj.ham_safar.app.trips.create.location.presentation.TripLocationScreenState.Companion.SECOND_ITEM_INDEX
import core.domain.cities.model.City
import core.domain.cities.use_case.GetCities

class TripLocationViewModel(
    private val getCities: GetCities,
    private val setTripLocationUseCase: SetTripLocationUseCase,
    private val getTripLocationUseCase: GetTripLocationUseCase,
    private val coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private var initialCities = emptyList<City>()

    private val _state = MutableStateFlow(TripLocationScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        TripLocationScreenState()
    ).toCommonStateFlow()

    init {
        tryToGetAlreadySetLocation()
        getCities()
    }


    fun onEvent(event: TripLocationEvent) {
        when (event) {
            TripLocationEvent.ResetState -> _state.update {
                it.copy(
                    shouldGoNext = false,
                    error = null
                )
            }

            TripLocationEvent.GoNext -> onGoNext()

            is TripLocationEvent.OnCityFromPicked -> _state.update { screenState ->
                val cityFrom = screenState.cities?.find { it.name == event.cityName }
                screenState.copy(
                    cityFrom = cityFrom,
                    isPickingCityFrom = false,
                    cities = getFilteredCities(cityFrom, screenState.cityTo)
                )
            }

            is TripLocationEvent.AddressFromTyped -> _state.update { screenState ->
                screenState.copy(addressFrom = event.text)
            }

            is TripLocationEvent.OnCityToPicked -> _state.update { screenState ->
                val cityTo = screenState.cities?.find { it.name == event.cityName }
                screenState.copy(
                    cityTo = cityTo,
                    isPickingCityTo = false,
                    cities = getFilteredCities(screenState.cityFrom, cityTo)
                )
            }

            is TripLocationEvent.AddressToTyped -> _state.update { screenState ->
                screenState.copy(addressTo = event.text)
            }

            TripLocationEvent.PickCityFrom -> _state.update {
                it.copy(isPickingCityFrom = true)
            }

            TripLocationEvent.PickCityTo -> _state.update {
                it.copy(isPickingCityTo = true)
            }

            TripLocationEvent.SwapLocations -> swapLocations()

            TripLocationEvent.OnCancelPickingCityFrom -> _state.update {
                it.copy(isPickingCityFrom = false)
            }

            TripLocationEvent.OnCancelPickingCityTo -> _state.update {
                it.copy(isPickingCityTo = false)
            }
            else -> Unit
        }
    }

    private fun getFilteredCities(
        cityFrom: City?,
        cityTo: City?
    ): List<City> = initialCities.filter { city -> city != cityFrom && city != cityTo }

    private fun onGoNext() {
        val state = state.value
        val isValidCities = state.cityFrom != null && state.cityTo != null
        val isValidAddress =
            !(state.addressFrom.isNullOrBlank() || state.addressTo.isNullOrBlank())
        val isValid = isValidCities && isValidAddress

        if (isValid) {
            setTripLocationUseCase(
                TripLocation(
                    cityFrom = state.cityFrom!!,
                    cityTo = state.cityTo!!,
                    addressFrom = state.addressFrom!!,
                    addressTo = state.addressTo!!
                )
            )
            _state.update { screenState ->
                screenState.copy(shouldGoNext = isValid)
            }

        } else {
            _state.update { screenState ->
                screenState.copy(error = Strings.enterData)
            }
        }
    }

    private fun swapLocations() {
        _state.update { screenState ->
            val locationFrom = screenState.cityFrom
            val locationTo = screenState.cityTo
            val addressFrom = screenState.addressFrom
            val addressTo = screenState.addressTo
            screenState.copy(
                cityFrom = locationTo,
                cityTo = locationFrom,
                addressFrom = addressTo,
                addressTo = addressFrom
            )
        }
    }

    private fun tryToGetAlreadySetLocation() {
        val location = getTripLocationUseCase()
        if (location != null) {
            _state.update {
                it.copy(
                    cityFrom = location.cityFrom,
                    cityTo = location.cityTo,
                    addressFrom = location.addressFrom,
                    addressTo = location.addressTo
                )
            }
        }
    }

    private fun getCities() = viewModelScope.launch {
        when (val result = getCities.execute()) {
            is Resource.Error -> _state.update {
                it.copy(
                    error = result.throwable?.message,
                    isLoading = false
                )
            }

            is Resource.Success -> _state.update {
                initialCities = result.data.orEmpty()
                val cityFrom = it.cityFrom ?: initialCities.firstOrNull()
                val cityTo = it.cityTo ?: initialCities.getOrNull(SECOND_ITEM_INDEX)
                it.copy(
                    isLoading = false,
                    cities = getFilteredCities(cityFrom, cityTo),
                    cityFrom = cityFrom,
                    cityTo = cityTo
                )
            }
        }
    }
}