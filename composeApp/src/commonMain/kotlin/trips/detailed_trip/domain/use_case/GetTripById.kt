package tj.ham_safar.app.trips.detailed_trip.domain.use_case

import core.domain.util.Resource
import trips.all.domain.models.Trip
import tj.ham_safar.app.trips.detailed_trip.data.remote.DetailedTripClient

class GetTripById(
    private val detailedTripClient: DetailedTripClient
) {
    suspend fun execute(id: Int): Resource<Trip> = detailedTripClient.getTripById(id)
}