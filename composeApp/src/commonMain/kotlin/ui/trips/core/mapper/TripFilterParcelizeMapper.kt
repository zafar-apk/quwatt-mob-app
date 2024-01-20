package ui.trips.core.mapper

import core.domain.cities.model.City
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType
import trips.all.domain.models.TripsFilter
import trips.filter.presentation.TripFilterScreenState
import ui.core.presentation.models.CityParcelize
import ui.trips.core.models.TripsFilter as UiTripsFilter

fun TripFilterScreenState.toTripsFilterParcelize() = UiTripsFilter(
    autoType = selectedState.selectedAutoType,
    autoModel = selectedState.selectedAutoModel,
    fromPriceTrip = selectedState.selectedFromPriceTrip.toInt(),
    toPriceTrip = selectedState.selectedToPriceTrip.toInt(),
    fromCity = selectedState.selectedFromCity?.let { CityParcelize(it.id, it.name) },
    toCity = selectedState.selectedToCity?.let { CityParcelize(it.id, it.name) },
    tripDate = selectedState.selectedTripDate,
    tripTime = selectedState.selectedTripTime,
    tripRating = selectedState.selectedTripRating?.toDouble()
)

fun UiTripsFilter.toTripsFilter() = TripsFilter(
    autoType = autoType?.let(TransportType::findByDisplayName),
    autoModel = autoModel?.let(TransportBrand::findByDisplayName),
    fromPriceTrip = fromPriceTrip,
    toPriceTrip = toPriceTrip,
    fromCity = fromCity?.let { City(it.id, it.name) },
    toCity = toCity?.let { City(it.id, it.name) },
    tripDate = tripDate,
    tripTime = tripTime,
    tripRating = tripRating
)

