package ui.trips.detailed_trip.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.detailed_trip.domain.use_case.GetTripById
import tj.ham_safar.app.trips.detailed_trip.presentation.DetailedTripScreenEvent
import tj.ham_safar.app.trips.detailed_trip.presentation.DetailedTripScreenState
import tj.ham_safar.app.trips.detailed_trip.presentation.DetailedTripScreenViewModel

class DetailedTripScreenAndroidViewModel(
    private val getTripById: GetTripById
) : ViewModel() {
    private val viewModel by lazy {
        DetailedTripScreenViewModel(
            coroutineScope = viewModelScope,
            getTripById = getTripById
        )
    }
    val state: CommonStateFlow<DetailedTripScreenState> = viewModel.state

    fun onEvent(event: DetailedTripScreenEvent) = viewModel.onEvent(event)
}