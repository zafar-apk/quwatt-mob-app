package trips.all.presentation

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
import trips.all.domain.use_case.GetAllTrips
import ui.trips.core.mapper.toTripsFilter
import user.domain.UserInteractor

class TripsScreenViewModel(
    private val getAllTrips: GetAllTrips,
    private val userInteractor: UserInteractor,
) : MoleculeViewModel<TripsScreenEvent, TripsScreenState>() {

    @Composable
    override fun getState(events: Flow<TripsScreenEvent>): TripsScreenState {
        return AllTripsPresenter(
            events,
            userInteractor,
            getAllTrips
        )
    }

    fun onEvent(event: TripsScreenEvent) {
        take(event)
    }
}

@Composable
fun AllTripsPresenter(
    events: Flow<TripsScreenEvent>,
    userInteractor: UserInteractor,
    getAllTripsUseCase: GetAllTrips
): TripsScreenState {
    val mutableState = remember { mutableStateOf(TripsScreenState()) }
    var state by mutableState

    LaunchedEffect(true) {
        events.collect { event ->
            when (event) {
                is TripsScreenEvent.RequestOpenTripItem -> onRequestOpenTripDetails(
                    event.id,
                    mutableState, userInteractor
                )

                is TripsScreenEvent.RequestOpenBookedTripItem -> {
                    state = state.copy(bookedTripDetail = event.id)
                }

                TripsScreenEvent.ResetNavigation -> {
                    state = state.copy(
                        tripDetail = null,
                        bookedTripDetail = null,
                        createTrip = false,
                        shouldRequestAuthorization = false,
                        shouldRegisterUserInfo = false,
                        shouldRegisterLicenceAndTransport = false
                    )
                }

                TripsScreenEvent.LoadTrips -> getAllTrips(mutableState, getAllTripsUseCase)

                is TripsScreenEvent.CreateTrip -> onCreateTrip(mutableState, userInteractor)

                is TripsScreenEvent.TripsFilterData -> {
                    state = state.copy(
                        tripsFilter = event.filter,
                        city = event.filter?.fromCity?.name
                    )
                    getAllTrips(mutableState, getAllTripsUseCase)
                }

                TripsScreenEvent.CancelShowingAuthDialog -> {
                    state = state.copy(shouldRequestAuthorization = false)
                }

                else -> Unit
            }
        }
    }
    return state
}

private suspend fun onCreateTrip(
    mutableState: MutableState<TripsScreenState>,
    userInteractor: UserInteractor
) = with(mutableState) {
    when {
        userInteractor.isAuthorized().not() -> {
            value = value.copy(shouldRequestAuthorization = true)
        }

        userInteractor.isUserExist().not() -> {
            value = value.copy(shouldRegisterUserInfo = true)
        }

        value.isDriver != true -> {
            value = value.copy(shouldRegisterLicenceAndTransport = true)
        }

        else -> {
            value = value.copy(createTrip = true)
        }
    }
}

private suspend fun onRequestOpenTripDetails(
    id: Int,
    mutableState: MutableState<TripsScreenState>,
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
            mutableState.value = mutableState.value.copy(tripDetail = id)
        }
    }
}

private suspend fun getAllTrips(
    state: MutableState<TripsScreenState>,
    getAllTrips: GetAllTrips
) {
    state.value = state.value.copy(isLoading = true, error = null)
    val filter = state.value.tripsFilter
    when (val result = getAllTrips.execute(filter?.toTripsFilter())) {
        is Resource.Success -> {
            state.value = state.value.copy(
                trips = result.data?.trips.orEmpty(),
                bookedTrips = result.data?.bookedTrips.orEmpty(),
                isLoading = false,
                isDriver = result.data?.isDriver
            )
        }

        is Resource.Error -> {
            state.value = state.value.copy(
                error = result.throwable?.message.toString(),
                isLoading = false,
                bookedTrips = emptyList(),
                trips = emptyList()
            )
        }
    }
}