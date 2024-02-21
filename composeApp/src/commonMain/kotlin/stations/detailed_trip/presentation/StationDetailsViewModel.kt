package stations.detailed_trip.presentation

import core.domain.util.Resource
import core.domain.util.toCommonStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import stations.detailed_trip.domain.use_case.GetStationById

class StationDetailsViewModel(
    private val getStationById: GetStationById
) : ViewModel() {
    private val _state = MutableStateFlow(StationDetailsState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        StationDetailsState()
    ).toCommonStateFlow()

    fun onEvent(event: StationDetailsEvent) {
        when (event) {
            is StationDetailsEvent.LoadStationDetails -> getStationDetails(event.id)
        }
    }

    private fun getStationDetails(id: Int) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        when (val result = getStationById.execute(id)) {

            is Resource.Success -> _state.update {
                it.copy(
                    connectors = result.data?.connectors.orEmpty(),
                    station = result.data?.station,
                    isLoading = false
                )
            }

            is Resource.Error -> _state.update {
                it.copy(error = result.throwable?.message.toString(), isLoading = false)
            }
        }
    }
}