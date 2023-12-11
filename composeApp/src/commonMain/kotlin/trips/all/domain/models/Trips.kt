package trips.all.domain.models

import trips.all.domain.models.BookedTrip
import trips.all.domain.models.Trip

data class Trips(
    val isDriver: Boolean?,
    val bookedTrips: List<BookedTrip>,
    val trips: List<Trip>,
)