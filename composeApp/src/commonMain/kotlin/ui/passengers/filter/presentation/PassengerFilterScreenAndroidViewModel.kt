package ui.passengers.filter.presentation

import core.domain.cities.use_case.GetCities
import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import passengers.filter.domain.usecases.GetAvailablePassengerTrips
import passengers.filter.presentation.PassengerFilterScreenEvent
import passengers.filter.presentation.PassengerFilterScreenState
import passengers.filter.presentation.PassengerFilterScreenViewModel

class PassengerFilterScreenAndroidViewModel(
    getCities: GetCities,
    getAvailablePassengerTrips: GetAvailablePassengerTrips
) : ViewModel() {
    private val viewModel by lazy {
        PassengerFilterScreenViewModel(
            coroutineScope = viewModelScope,
            getCities = getCities,
            getAvailablePassengerTrips = getAvailablePassengerTrips
        )
    }

    val state: CommonStateFlow<PassengerFilterScreenState> = viewModel.state

    fun onEvent(event: PassengerFilterScreenEvent) = viewModel.onEvent(event)
}