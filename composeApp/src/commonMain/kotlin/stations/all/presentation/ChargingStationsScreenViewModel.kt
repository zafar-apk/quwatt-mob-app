package stations.all.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import core.domain.util.Resource
import core.presentation.MoleculeViewModel
import kotlinx.coroutines.flow.Flow
import stations.all.domain.use_case.GetAllStations
import ui.stations.core.mapper.toTripsFilter
import user.domain.UserInteractor

class ChargingStationsScreenViewModel(
    private val getAllStations: GetAllStations,
    private val userInteractor: UserInteractor,
) : MoleculeViewModel<StationsScreenEvent, StationsScreenState>(
    initialEvent = StationsScreenEvent.LoadStations
) {

    @Composable
    override fun getState(events: Flow<StationsScreenEvent>): StationsScreenState {
        return AllTripsPresenter(
            events,
            userInteractor,
            getAllStations
        )
    }

    fun onEvent(event: StationsScreenEvent) {
        take(event)
    }
}

@Composable
fun AllTripsPresenter(
    events: Flow<StationsScreenEvent>,
    userInteractor: UserInteractor,
    getAllStations: GetAllStations
): StationsScreenState {
    val mutableState = remember { mutableStateOf(StationsScreenState()) }
    var state by mutableState

    LaunchedEffect(true) {
        events.collect { event ->
            when (event) {
                is StationsScreenEvent.RequestToOpenChargingStation -> onRequestOpenTripDetails(
                    event.id,
                    mutableState, userInteractor
                )

                StationsScreenEvent.ResetNavigation -> {
                    state = state.copy(
                        stationDetail = null,
                        shouldRequestAuthorization = false,
                        shouldRegisterUserInfo = false
                    )
                }

                StationsScreenEvent.LoadStations -> getAllStations(mutableState, getAllStations)


                is StationsScreenEvent.StationsFilterData -> {
                    state = state.copy(stationsFilter = event.filter)
                    getAllStations(mutableState, getAllStations)
                }

                StationsScreenEvent.CancelShowingAuthDialog -> {
                    state = state.copy(shouldRequestAuthorization = false)
                }

                else -> Unit
            }
        }
    }
    return state
}

private suspend fun onRequestOpenTripDetails(
    id: Int,
    mutableState: MutableState<StationsScreenState>,
    userInteractor: UserInteractor
) {
    when {
        userInteractor.isAuthorized().not() -> {
            mutableState.value = mutableState.value.copy(shouldRequestAuthorization = true)
        }

        userInteractor.isUserExist().not() -> {
            mutableState.value = mutableState.value.copy(shouldRegisterUserInfo = true)
        }

        else -> {
            mutableState.value = mutableState.value.copy(stationDetail = id)
        }
    }
}

private suspend fun getAllStations(
    state: MutableState<StationsScreenState>,
    getAllStations: GetAllStations
) {
    state.value = state.value.copy(isLoading = true, error = null)
    val filter = state.value.stationsFilter
    when (val result = getAllStations.execute(filter?.toTripsFilter())) {
        is Resource.Success -> {
            state.value = state.value.copy(
                stations = result.data.orEmpty(),
                isLoading = false,
            )
        }

        is Resource.Error -> {
            state.value = state.value.copy(
                error = result.throwable?.message.toString(),
                isLoading = false,
                stations = emptyList()
            )
        }
    }
}