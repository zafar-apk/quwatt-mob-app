package passengers.filter.presentation

import core.domain.cities.model.City

data class PassengerFilterScreenState(
    val selectedState: PassengerFilterSelectedState = PassengerFilterSelectedState(),
    val choosingState: PassengerFilterChoosingState = PassengerFilterChoosingState(),
    val resetPriceValues: Boolean = false,
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val error: String? = null,
    val shouldGoBackWithResult: Boolean = false,
    val availableTrips: Int = 0,
)


data class PassengerFilterSelectedState(
    val selectedFromPriceTrip: Int = 0,
    val selectedToPriceTrip: Int = 500,
    val selectedFromCity: City? = null,
    val selectedToCity: City? = null,
    val selectedTripDate: String? = null,
    val selectedTripTime: String? = null,
    val selectedTripRating: Int? = null,
)

data class PassengerFilterChoosingState(
    val isChoosingFromCity: Boolean = false,
    val isChoosingDate: Boolean = false,
    val isChoosingTimeDate: Boolean = false,
    val isChoosingRating: Boolean = false,
    val isChoosingToCity: Boolean = false,
)

