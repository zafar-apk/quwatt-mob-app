package ui.passengers.details.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import passengers.detail.domain.GetPassengerById
import passengers.detail.presentation.DetailedPassengerScreenEvent
import passengers.detail.presentation.DetailedPassengerScreenState
import passengers.detail.presentation.DetailedPassengerScreenViewModel


class DetailedPassengerScreenAndroidViewModel (
    private val getPassengerById: GetPassengerById
) : ViewModel() {
    private val viewModel by lazy {
        DetailedPassengerScreenViewModel(
            coroutineScope = viewModelScope,
            getPassengerById = getPassengerById
        )
    }
    val state: CommonStateFlow<DetailedPassengerScreenState> = viewModel.state

    fun onEvent(event: DetailedPassengerScreenEvent) = viewModel.onEvent(event)
}