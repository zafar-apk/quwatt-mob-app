package trips.all.choose_spot.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import core.domain.util.toCommonStateFlow
import trips.all.choose_spot.presentation.ChooseSpotScreenEvent
import trips.all.choose_spot.presentation.ChooseSpotScreenState

class ChooseSpotScreenViewModel(
    private val coroutineScope: CoroutineScope?

) {
    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private val _state = MutableStateFlow(ChooseSpotScreenState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        ChooseSpotScreenState()
    ).toCommonStateFlow()

    fun onEvent(event: ChooseSpotScreenEvent) {
        when (event) {
            /*is ChooseSpotScreenEvent.RequestOpenTripItem -> onRequestOpenTripDetails(event.id)

            ChooseSpotScreenEvent.ResetNavigation -> _state.update {
                it.copy(
                    tripDetail = null,
                    shouldRequestAuthorization = false
                )
            }
*/
            else -> Unit
        }
    }

}