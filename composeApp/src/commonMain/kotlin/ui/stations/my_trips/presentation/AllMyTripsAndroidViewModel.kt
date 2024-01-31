package ui.stations.my_trips.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import tj.ham_safar.app.trips.my_trips.domain.GetAllMyTrips
import stations.my_trips.presentation.MyTripsScreenEvent
import tj.ham_safar.app.trips.my_trips.presentation.MyTripsScreenState
import tj.ham_safar.app.trips.my_trips.presentation.MyTripsScreenViewModel

class AllMyTripsAndroidViewModel (
    getAllMyTrips: GetAllMyTrips
) : ViewModel() {

    private val viewModel = MyTripsScreenViewModel(
        getAllMyTrips = getAllMyTrips,
        coroutineScope = viewModelScope
    )

    val state: CommonStateFlow<MyTripsScreenState> = viewModel.state

    fun onEvent(event: MyTripsScreenEvent) = viewModel.onEvent(event)
}