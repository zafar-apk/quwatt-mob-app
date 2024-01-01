package trips.all.presentation

import trips.all.domain.models.Trip
import ui.trips.core.models.TripsFilter

data class TripsScreenState(
    val trips: List<Trip> = emptyList(),
    val city: String? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val tripDetail: Int? = null,
    val bookedTripDetail: Int? = null,
    val createTrip: Boolean = false,
    val shouldRequestAuthorization: Boolean = false,
    val shouldRegisterUserInfo: Boolean = false,
    val shouldRegisterLicenceAndTransport: Boolean = false,
    val isDriver: Boolean? = null,
    val tripsFilter: TripsFilter? = null
)