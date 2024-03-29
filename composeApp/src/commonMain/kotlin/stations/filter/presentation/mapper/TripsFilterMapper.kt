package tj.ham_safar.app.trips.filter.presentation.mapper

import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType
import stations.all.domain.models.StationsFilter
import stations.filter.presentation.TripFilterScreenState

fun TripFilterScreenState.copyFilterFields(filter: StationsFilter) = copy(
    selectedState = selectedState.copy(
        selectedAutoType = filter.autoType?.displayName,
        selectedAutoModel = filter.autoModel?.displayName,
        selectedFromPriceTrip = filter.fromPriceTrip.toFloat(),
        selectedToPriceTrip = filter.toPriceTrip.toFloat(),
        selectedFromCity = filter.fromCity,
        selectedToCity = filter.toCity,
        selectedTripDate = filter.tripDate,
        selectedTripTime = filter.tripTime,
        selectedTripRating = filter.tripRating?.toInt(),
    )
)

fun TripFilterScreenState.toTripsFilter() = StationsFilter(
    autoType = selectedState.selectedAutoType?.let { TransportType.findByDisplayName(it) },
    autoModel = selectedState.selectedAutoModel?.let { TransportBrand.findByDisplayName(it) },
    fromPriceTrip = selectedState.selectedFromPriceTrip.toInt(),
    toPriceTrip = selectedState.selectedToPriceTrip.toInt(),
    fromCity = selectedState.selectedFromCity,
    toCity = selectedState.selectedToCity,
    tripDate = selectedState.selectedTripDate,
    tripTime = selectedState.selectedTripTime,
    tripRating = selectedState.selectedTripRating?.toDouble(),
)