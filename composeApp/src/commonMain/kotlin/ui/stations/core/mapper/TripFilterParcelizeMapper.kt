package ui.stations.core.mapper

import core.domain.cities.model.City
import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType
import stations.all.domain.models.StationsFilter
import stations.filter.presentation.TripFilterScreenState
import ui.core.presentation.models.CityParcelize
import ui.stations.core.models.StationsFilter as UiTripsFilter

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

fun UiTripsFilter.toTripsFilter() = StationsFilter(
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

