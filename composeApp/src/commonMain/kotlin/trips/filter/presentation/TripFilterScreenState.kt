package trips.filter.presentation

import core.domain.cities.model.City
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType

data class TripFilterScreenState(
    val selectedState: TripFilterSelectedState = TripFilterSelectedState(),
    val choosingState: TripFilterChoosingState = TripFilterChoosingState(),
    val resetPriceValues: Boolean = false,
    val isLoading: Boolean = false,
    val cities: List<City> = emptyList(),
    val autoTypes: List<String> = TransportType.entries.map { it.displayName },
    val autoModels: List<String> = TransportBrand.entries.map { it.displayName },
    val availableTrips: Int = 0,
    val error: String? = null,
    val shouldGoBackWithResult: Boolean = false,
)

data class TripFilterSelectedState(
    val selectedAutoType: String? = null,
    val selectedAutoModel: String? = null,
    val selectedFromPriceTrip: Float = 0F,
    val selectedToPriceTrip: Float = 500F,
    val selectedFromCity: City? = null,
    val selectedToCity: City? = null,
    val selectedTripDate: String? = null,
    val selectedTripTime: String? = null,
    val selectedTripRating: Int? = null,
)

data class TripFilterChoosingState(
    val isChoosingAutoModel: Boolean = false,
    val isChoosingAutoType: Boolean = false,
    val isChoosingFromCity: Boolean = false,
    val isChoosingDate: Boolean = false,
    val isChoosingTimeDate: Boolean = false,
    val isChoosingRating: Boolean = false,
    val isChoosingToCity: Boolean = false,
)

