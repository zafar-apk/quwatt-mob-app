package stations.detailed_trip.presentation

import stations.all.domain.models.Station

data class DetailedTripScreenState(
    val station: Station? = null,
    val error: String? = null,
    val isLoading: Boolean = false,
    val shouldOpenBookingTrip: Boolean = false
)