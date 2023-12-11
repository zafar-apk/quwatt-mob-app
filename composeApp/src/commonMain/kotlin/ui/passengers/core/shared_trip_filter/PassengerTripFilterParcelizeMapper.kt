package ui.passengers.core.shared_trip_filter

import core.domain.cities.model.City
import passengers.all.domain.PassengerFilter
import passengers.filter.presentation.PassengerFilterScreenState
import ui.core.presentation.models.CityParcelize

fun PassengerFilterScreenState.toTripsFilterParcelize() = PassengerTripsFilterParcelize(
    fromPriceTrip = selectedState.selectedFromPriceTrip,
    toPriceTrip = selectedState.selectedToPriceTrip,
    fromCity = selectedState.selectedFromCity?.let { CityParcelize(it.id, it.name) },
    toCity = selectedState.selectedToCity?.let { CityParcelize(it.id, it.name) },
    tripDate = selectedState.selectedTripDate,
    tripTime = selectedState.selectedTripTime,
    tripRating = selectedState.selectedTripRating?.toDouble()
)

fun PassengerTripsFilterParcelize.toPassengerTripsFilter() = PassengerFilter(
    fromPriceTrip = fromPriceTrip,
    toPriceTrip = toPriceTrip,
    fromCity = fromCity?.let { City(it.id, it.name) },
    toCity = toCity?.let { City(it.id, it.name) },
    tripDate = tripDate,
    tripTime = tripTime,
    tripRating = tripRating
)

