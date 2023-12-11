package passengers.all.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import core.domain.util.Resource
import core.domain.util.toCommonFlow
import passengers.all.domain.GetAllPassengers
import user.domain.UserInteractor

class PassengersScreenViewModel(
    private val getAllPassengers: GetAllPassengers,
    private val userInteractor: UserInteractor,
    private val coroutineScope: CoroutineScope?
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)
    private var allPassengersLoadingJob: Job? = null

    private val _state = MutableStateFlow(PassengersScreenState())
    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = PassengersScreenState()
    )
        .onStart { getAllPassengers() }
        .toCommonFlow()


    fun onEvent(event: PassengersScreenEvent) {
        when (event) {
            PassengersScreenEvent.AddNewPassenger -> openAddNewPassengerOrRequestAuthorization()
            is PassengersScreenEvent.RequestToOpenPassengerDetails -> openPassengerOrRequestAuthorization(
                event.id
            )
            PassengersScreenEvent.ResetNavigation -> _state.update {
                it.copy(
                    shouldRequestAuthorization = false,
                    shouldRegisterUserInfo = false,
                    openAddNewPassenger = false,
                    openPassenger = null
                )
            }
            PassengersScreenEvent.LoadPassengers -> getAllPassengers()

            is PassengersScreenEvent.TripsFilterData -> _state.update {
                val city = event.filter.fromCity?.name
                it.copy(tripsFilter = event.filter, city = city)
            }.also { getAllPassengers() }

            else -> Unit
        }
    }

    private fun getAllPassengers() {
        allPassengersLoadingJob?.cancel()

        allPassengersLoadingJob = viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            val filter = _state.value.tripsFilter

            when (val result = getAllPassengers.execute(filter)) {

                is Resource.Error -> _state.update {
                    it.copy(
                        errorMessage = result.throwable?.message,
                        isLoading = false,
                        passengers = emptyList()
                    )
                }
                is Resource.Success -> _state.update {
                    it.copy(passengers = result.data.orEmpty(), isLoading = false)
                }
            }
        }
    }

    private fun openPassengerOrRequestAuthorization(id: Int) = viewModelScope.launch {
        _state.update {
            if (userInteractor.isAuthorized()) {
                it.copy(openPassenger = id)
            } else {
                it.copy(shouldRequestAuthorization = true)
            }
        }
    }

    private fun openAddNewPassengerOrRequestAuthorization() = viewModelScope.launch {
        _state.update {
            if (userInteractor.isAuthorized().not()) {
                it.copy(shouldRequestAuthorization = true)
            } else if (userInteractor.isUserExist().not())
                it.copy(shouldRegisterUserInfo = true)
            else {
                it.copy(openAddNewPassenger = true)
            }
        }
    }
}