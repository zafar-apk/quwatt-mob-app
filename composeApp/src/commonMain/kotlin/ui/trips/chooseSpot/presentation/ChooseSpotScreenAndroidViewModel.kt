package ui.trips.chooseSpot.presentation

import core.domain.util.CommonStateFlow
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import trips.all.choose_spot.presentation.ChooseSpotScreenEvent
import trips.all.choose_spot.presentation.ChooseSpotScreenState
import trips.all.choose_spot.presentation.ChooseSpotScreenViewModel

class ChooseSpotScreenAndroidViewModel : ViewModel() {
    private val viewModel by lazy {
        ChooseSpotScreenViewModel(
            coroutineScope = viewModelScope,
        )
    }
    val state: CommonStateFlow<ChooseSpotScreenState> = viewModel.state

    fun onEvent(event: ChooseSpotScreenEvent) = viewModel.onEvent(event)
}