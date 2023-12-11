package trips.all.data.remote.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import trips.all.data.remote.model.BookedTripDTO
import trips.all.data.remote.model.TripDTO

@Serializable
class TripsDTO(
    @SerialName("isDriver")
    val isDriver: Boolean?,
    @SerialName("trips")
    val trips: List<TripDTO>,
    @SerialName("bookedTrips")
    val bookedTrips: List<BookedTripDTO>,
)