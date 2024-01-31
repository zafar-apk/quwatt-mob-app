package ui.stations.create.passengers.presentation

import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.CreateTripUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripPricingUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripPricingUseCase
import tj.ham_safar.app.trips.create.passengers.presentation.TripPassengersScreenEvent
import tj.ham_safar.app.trips.create.passengers.presentation.TripPassengersViewModel

class TripPassengerAndroidViewModel (
    getTripPricingUseCase: GetTripPricingUseCase,
    setTripPricingUseCase: SetTripPricingUseCase,
    createTripUseCase: CreateTripUseCase
) : ViewModel() {

    private val viewModel = TripPassengersViewModel(
        getTripPricingUseCase = getTripPricingUseCase,
        setTripPricingUseCase = setTripPricingUseCase,
        createTripUseCase = createTripUseCase,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: TripPassengersScreenEvent) = viewModel.onEvent(event)
}