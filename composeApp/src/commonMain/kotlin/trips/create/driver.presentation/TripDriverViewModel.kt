package tj.ham_safar.app.trips.create.driver.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import core.domain.Strings
import core.domain.util.Resource
import trips.all.domain.models.Seat
import trips.core.create_trip.domain.errors.TripCreationError
import tj.ham_safar.app.trips.core.create_trip.domain.models.TripPricing
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.CreateTripUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.GetTripPricingUseCase
import tj.ham_safar.app.trips.core.create_trip.domain.usecases.SetTripPricingUseCase

class TripDriverViewModel(
    private val getTripPricingUseCase: GetTripPricingUseCase,
    private val setTripPricingUseCase: SetTripPricingUseCase,
    private val createTripUseCase: CreateTripUseCase,
    private val coroutineScope: CoroutineScope? = null
) {

    private val viewModelScope = coroutineScope ?: CoroutineScope(Dispatchers.Main)

    private val _state = MutableStateFlow(TripDriverScreenState())

    val state = _state.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TripDriverScreenState()
    )

    init {
        tryToGetAlreadySetPrice()
    }

    fun onEvent(event: TripDriverScreenEvent) {
        when (event) {

            TripDriverScreenEvent.CreateTrip -> validateAndCreateTrip()

            TripDriverScreenEvent.OnCountMinus -> {
                if (state.value.count > 1)
                    _state.update { screenState ->
                        screenState.copy(count = screenState.count - 1)
                    }
            }

            TripDriverScreenEvent.OnCountPlus -> _state.update { screenState ->
                screenState.copy(count = screenState.count + 1)
            }

            is TripDriverScreenEvent.OnPriceChanged -> _state.update {
                it.copy(price = event.price)
            }

            TripDriverScreenEvent.ResetState -> _state.update { screenState ->
                screenState.copy(
                    shouldGoNext = false,
                    error = null
                )
            }
            else -> Unit
        }
    }

    private fun validateAndCreateTrip() {
        val state = state.value
        if (state.price == 0)
            _state.value = state.copy(error = Strings.enterData)
        else
            leaveRequest()
    }

    private fun leaveRequest() = viewModelScope.launch {
        val screenState = state.value
        setTripPricingUseCase(
            TripPricing.TripDriverPricing(createSeats(), screenState.price)
        )
        _state.update { it.copy(isRequestSent = true) }
        val resource = createTripUseCase()
        when (resource) {
            is Resource.Success -> _state.update { it.copy(shouldGoNext = true) }
            is Resource.Error -> processError(resource)
        }
    }

    private fun processError(resource: Resource.Error<Unit>) =
        _state.update {
            val error = resource.throwable
            if (error is TripCreationError)
                when (error) {
                    is TripCreationError.LocationMissingError -> it.copy(shouldGoToTripLocation = true)
                    is TripCreationError.DateTimeMissingError -> it.copy(shouldGoToTripDateTime = true)
                    is TripCreationError.PricingMissingError -> it.copy(error = Strings.enterData)

                }
            else it.copy(
                error = resource.throwable?.message,
                isRequestSent = false
            )
        }


    private fun tryToGetAlreadySetPrice() {
        val price = getTripPricingUseCase() as? TripPricing.TripDriverPricing ?: return
        _state.update {
            it.copy(
                count = price.seats.size,
                price = price.price
            )
        }
    }

    private fun createSeats(): List<Seat> =
        state.value.let { state ->
            List(state.count) { index ->
                Seat(
                    id = index,
                    position = "FRONT",
                    userId = null,
                    price = state.price
                )
            }
        }

}