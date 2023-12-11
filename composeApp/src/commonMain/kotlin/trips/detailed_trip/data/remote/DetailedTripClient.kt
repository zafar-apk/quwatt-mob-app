package tj.ham_safar.app.trips.detailed_trip.data.remote

import core.domain.util.Resource
import trips.all.domain.models.Trip

interface DetailedTripClient {
    suspend fun getTripById(id: Int): Resource<Trip>
}