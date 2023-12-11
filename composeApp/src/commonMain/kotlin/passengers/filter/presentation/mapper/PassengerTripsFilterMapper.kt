package passengers.filter.presentation.mapper

import passengers.all.domain.PassengerFilter
import passengers.filter.presentation.PassengerFilterScreenState

fun PassengerFilterScreenState.copyFilterFields(filter: PassengerFilter) = copy(
    selectedState = selectedState.copy(
        selectedFromPriceTrip = filter.fromPriceTrip,
        selectedToPriceTrip = filter.toPriceTrip,
        selectedFromCity = filter.fromCity,
        selectedToCity = filter.toCity,
        selectedTripDate = filter.tripDate,
        selectedTripTime = filter.tripTime,
        selectedTripRating = filter.tripRating?.toInt(),
    )
)

fun PassengerFilterScreenState.toPassengerTripsFilter() = PassengerFilter(
    fromPriceTrip = selectedState.selectedFromPriceTrip,
    toPriceTrip = selectedState.selectedToPriceTrip,
    fromCity = selectedState.selectedFromCity,
    toCity = selectedState.selectedToCity,
    tripDate = selectedState.selectedTripDate,
    tripTime = selectedState.selectedTripTime,
    tripRating = selectedState.selectedTripRating?.toDouble(),
)