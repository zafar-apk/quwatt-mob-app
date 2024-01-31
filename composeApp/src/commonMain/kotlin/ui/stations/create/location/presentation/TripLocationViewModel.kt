package ui.stations.create.location.presentation

import core.domain.cities.use_case.GetCities
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripLocationUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripLocationUseCase
import tj.ham_safar.app.trips.create.location.presentation.TripLocationEvent
import tj.ham_safar.app.trips.create.location.presentation.TripLocationViewModel

class TripLocationAndroidViewModel (
    private val setTripLocationUseCase: SetTripLocationUseCase,
    private val getTripLocationUseCase: GetTripLocationUseCase,
    private val getCities: GetCities
) : ViewModel() {

    private val viewModel = TripLocationViewModel(
        setTripLocationUseCase = setTripLocationUseCase,
        getTripLocationUseCase = getTripLocationUseCase,
        getCities = getCities,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: TripLocationEvent) = viewModel.onEvent(event)
}