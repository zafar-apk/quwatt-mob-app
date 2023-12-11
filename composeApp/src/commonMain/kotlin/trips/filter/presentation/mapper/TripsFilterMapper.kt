package tj.ham_safar.app.trips.filter.presentation.mapper

import profile.domain.transport.TransportBrand
import profile.domain.transport.TransportType
import trips.all.domain.models.TripsFilter
import trips.filter.presentation.TripFilterScreenState

fun TripFilterScreenState.copyFilterFields(filter: TripsFilter) = copy(
    selectedState = selectedState.copy(
        selectedAutoType = filter.autoType?.displayName,
        selectedAutoModel = filter.autoModel?.displayName,
        selectedFromPriceTrip = filter.fromPriceTrip,
        selectedToPriceTrip = filter.toPriceTrip,
        selectedFromCity = filter.fromCity,
        selectedToCity = filter.toCity,
        selectedTripDate = filter.tripDate,
        selectedTripTime = filter.tripTime,
        selectedTripRating = filter.tripRating?.toInt(),
    )
)

fun TripFilterScreenState.toTripsFilter() = TripsFilter(
    autoType = selectedState.selectedAutoType?.let { TransportType.findByDisplayName(it) },
    autoModel = selectedState.selectedAutoModel?.let { TransportBrand.findByDisplayName(it) },
    fromPriceTrip = selectedState.selectedFromPriceTrip,
    toPriceTrip = selectedState.selectedToPriceTrip,
    fromCity = selectedState.selectedFromCity,
    toCity = selectedState.selectedToCity,
    tripDate = selectedState.selectedTripDate,
    tripTime = selectedState.selectedTripTime,
    tripRating = selectedState.selectedTripRating?.toDouble(),
)