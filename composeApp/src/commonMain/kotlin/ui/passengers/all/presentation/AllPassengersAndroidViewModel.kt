package ui.passengers.all.presentation

import core.domain.util.CommonFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import passengers.all.domain.GetAllPassengers
import passengers.all.presentation.PassengersScreenEvent
import passengers.all.presentation.PassengersScreenState
import passengers.all.presentation.PassengersScreenViewModel
import user.domain.UserInteractor

class AllPassengersAndroidViewModel (
    getAllPassengers: GetAllPassengers,
    userInteractor: UserInteractor
) : ViewModel() {

    private val viewModel = PassengersScreenViewModel(
        getAllPassengers = getAllPassengers,
        userInteractor = userInteractor,
        coroutineScope = viewModelScope
    )

    val state: CommonFlow<PassengersScreenState> = viewModel.state

    fun onEvent(event: PassengersScreenEvent) = viewModel.onEvent(event)
}