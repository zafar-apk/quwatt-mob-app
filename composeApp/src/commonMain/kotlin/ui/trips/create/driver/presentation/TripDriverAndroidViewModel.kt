package ui.trips.create.driver.presentation

import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.CreateTripUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripPricingUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripPricingUseCase
import tj.ham_safar.app.trips.create.driver.presentation.TripDriverScreenEvent
import tj.ham_safar.app.trips.create.driver.presentation.TripDriverViewModel

class TripDriverAndroidViewModel (
    getTripPricingUseCase: GetTripPricingUseCase,
    setTripPricingUseCase: SetTripPricingUseCase,
    createTripUseCase: CreateTripUseCase
) : ViewModel() {

    private val viewModel = TripDriverViewModel(
        getTripPricingUseCase = getTripPricingUseCase,
        setTripPricingUseCase = setTripPricingUseCase,
        createTripUseCase = createTripUseCase,
        coroutineScope = viewModelScope
    )

    val state = viewModel.state

    fun onEvent(event: TripDriverScreenEvent) = viewModel.onEvent(event)
}